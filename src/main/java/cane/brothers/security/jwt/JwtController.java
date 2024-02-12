package cane.brothers.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/jwt")
@ConditionalOnProperty(value ="app.jwt.enabled", havingValue = "true")
@RequiredArgsConstructor
public class JwtController {

    private final JwtTokenService tokenSvc;

    @PostMapping(produces = {"application/json"})
    public JwtTokenResponse generate(@RequestBody Map<String, Object> claims) {
        var accessToken = tokenSvc.createAccessToken(claims, null);
        return new JwtTokenResponse(accessToken);
    }

    @GetMapping(value = "/{token}")
    public Map<String, Object> validate(@PathVariable String token) {
        return tokenSvc.getTokenClaims(token, false);
    }


}
