package cane.brothers.security;

import cane.brothers.AppProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwe;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
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
public class TokenProvider {

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

  public Claims getTokenClaims(String bearerToken) {
    try {
      var jwt = Jwts.parser()
          .verifyWith(this.keys)
          .build()
          .parse(bearerToken)
          .accept(Jwe.CLAIMS);
      return jwt.getPayload();
    } catch (SignatureException ex) {
      log.error("Invalid JWT signature", ex.getCause());
    } catch (MalformedJwtException ex) {
      log.error("Invalid JWT token", ex.getCause());
    } catch (ExpiredJwtException ex) {
      log.error("Expired JWT token", ex.getCause());
    } catch (UnsupportedJwtException ex) {
      log.error("Unsupported JWT token", ex.getCause());
    } catch (IllegalArgumentException ex) {
      log.error("JWT claims string is empty.", ex.getCause());
    }
    return null;
  }

}
