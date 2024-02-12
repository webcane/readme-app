package cane.brothers.security.jwt;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.BadJWTException;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

public interface JwtTokenService {

  String createAccessToken(@NotNull Map<String, Object> claims, String issuer);

  void validateClaims(JWTClaimsSet jwtClaimsSet) throws BadJWTException;

  Map<String, Object> getTokenClaims(String jwtToken, boolean verifyClaims);
}
