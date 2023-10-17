package cane.brothers.security.jwt;

import cane.brothers.AppProperties;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class JwtTokenServiceImpl implements JwtTokenService {


  private final JwtTokenProvider jwtTokenProvider;

  private final AppProperties appProperties;

  @Override
  public String createAccessToken() {
    Map<String, Object> claims = Map.of("iss", "readme-app",
        "role", "user",
        "sub", appProperties.auth().tokenSubject(),
        "aud", appProperties.auth().tokenAudience());
    return jwtTokenProvider.createAccessToken(claims);
  }
}
