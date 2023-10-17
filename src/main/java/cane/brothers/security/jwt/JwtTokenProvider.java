package cane.brothers.security.jwt;

import cane.brothers.AppProperties;
import cane.brothers.security.UserPrincipal;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author mniedre
 */
@Slf4j
@Service
@RequiredArgsConstructor
class JwtTokenProvider {

  private final AppProperties appProperties;

  private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
  private final SecretKey keys = Jwts.SIG.HS256.key().build();

  public String createAccessToken(Map<String, Object> claims) {
    var authentication = SecurityContextHolder.getContext().getAuthentication();

    String subj = appProperties.auth().tokenSubject();
    if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal userPrincipal) {
      subj = Long.toString(userPrincipal.getId());
    }

    ZonedDateTime expiryDate = ZonedDateTime.now().plusDays(appProperties.auth().tokenExpiration().toDays());
    var expiredAt = Date.from(expiryDate.toInstant());

    return createJwtToken(claims, subj, subj, expiredAt, keys, signatureAlgorithm);
  }

  protected String createJwtToken(Map<String, Object> claims, String audience, String subject,
                                  Date expiredAt, Key key, SignatureAlgorithm signatureAlgorithm) {
    var jwtBuilder = Jwts.builder()
        .id(UUID.randomUUID().toString())
        .audience().add(audience).and()
        .subject(subject)
        .issuedAt(new Date())
        .expiration(expiredAt)
        .claims().add(claims).and()
        .signWith(key, signatureAlgorithm);
    return jwtBuilder.compact();
  }
}
