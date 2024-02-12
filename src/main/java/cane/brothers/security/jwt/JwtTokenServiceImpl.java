package cane.brothers.security.jwt;

import cane.brothers.AppProperties;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.proc.BadJWTException;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTClaimsVerifier;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
class JwtTokenServiceImpl implements JwtTokenService {

    private static final JWSAlgorithm signatureAlgorithm = JWSAlgorithm.HS256;
    private final AppProperties appProperties;
    private final ZoneId zoneId = ZoneId.systemDefault();

    @Override
    public String createAccessToken(Map<String, Object> claims, String issuer) {
        log.info("Create an access token");
        claims.put("token_use", "access");
        var dt = LocalDateTime.now();
        var lastDay = dt.plusDays(appProperties.auth().tokenExpiration().toDays());
        var issuedAt = Date.from(dt.atZone(zoneId).toInstant());
        var expiredAt = Date.from(lastDay.atZone(zoneId).toInstant());

        try {
            return createJwtToken(claims, issuer == null ? appProperties.auth().tokenSubject() : issuer,
                    appProperties.auth().tokenSubject(),
                    appProperties.auth().tokenAudience(), issuedAt, expiredAt);
        } catch (JOSEException ex) {
            log.error(ex.getMessage(), ex.getCause());
            throw new JwtTokenException("Access token creation error", ex);
        }
    }

    @Override
    public void validateClaims(JWTClaimsSet jwtClaimsSet) throws BadJWTException {
        log.info("Verify token claims {}", jwtClaimsSet);
        var subject = appProperties.auth().tokenSubject();
        var audience = appProperties.auth().tokenAudience();
        verifyTokenClaims(jwtClaimsSet, subject, audience);
    }

    @Override
    public Map<String, Object> getTokenClaims(String jwtToken, boolean verifyClaims) {
        try {
            var jwtTokenClaimsSet = getTokenClaimsSet(jwtToken);
            if (verifyClaims) {
                validateClaims(jwtTokenClaimsSet);
            }
            return jwtTokenClaimsSet.getClaims();
        } catch (BadJOSEException | ParseException | JOSEException ex) {
            log.error("Error to parse jwt token. {}", jwtToken);
            log.error(ex.getMessage(), ex.getCause());
            var msg = verifyClaims ? "Error to verify jwt token" : "Error to parse jwt token claims";
            throw new JwtTokenException(msg, ex);
        }
    }

    protected String createJwtToken(Map<String, Object> claims, String issuer, String audience, String subject,
                                    Date issuedAt, Date expiredAt) throws JOSEException {
        var jwtId = getTokenId();
        log.info("Create jwt token with id {}", jwtId);
        var jwtBuilder = new JWTClaimsSet.Builder()
                .jwtID(jwtId)
                .subject(subject)
                .issuer(issuer)
                .audience(audience)
                .notBeforeTime(issuedAt)
                .expirationTime(expiredAt)
                .issueTime(issuedAt);

        for (var claimEntry : claims.entrySet()) {
            jwtBuilder.claim(claimEntry.getKey(), claimEntry.getValue());
        }

        var jwtClaims = jwtBuilder.build();
        Payload payload = new Payload(jwtClaims.toJSONObject());
        JWSObject jwsObject = new JWSObject(new JWSHeader(signatureAlgorithm), payload);
        jwsObject.sign(new MACSigner(appProperties.auth().tokenSecret()));
        return jwsObject.serialize();
    }

    protected String getTokenId() {
        return UUID.randomUUID().toString();
    }

    protected void verifyTokenClaims(JWTClaimsSet jwtClaimsSet, String subject, String audience) throws BadJWTException {
        DefaultJWTClaimsVerifier<?> verifier = new DefaultJWTClaimsVerifier<>(
                new JWTClaimsSet.Builder()
                        .subject(subject)
                        .audience(audience)
                        .build(),
                new HashSet<>(Arrays.asList("exp", "aud", "sub", "nbf", "jti")));
        verifier.verify(jwtClaimsSet, null);
    }

    protected JWTClaimsSet getTokenClaimsSet(String jwtToken) throws ParseException, JOSEException, BadJOSEException {
        var secretKey = appProperties.auth().tokenSecret().getBytes();

        SignedJWT signedJWT = SignedJWT.parse(jwtToken);
        signedJWT.verify(new MACVerifier(secretKey));

        ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();
        JWSKeySelector<SecurityContext> keySelector = new JWSVerificationKeySelector<>(signatureAlgorithm,
                new ImmutableSecret<>(secretKey));
        jwtProcessor.setJWSKeySelector(keySelector);
        jwtProcessor.process(signedJWT, null);
        return signedJWT.getJWTClaimsSet();
    }
}
