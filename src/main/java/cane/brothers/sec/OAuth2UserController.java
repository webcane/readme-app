package cane.brothers.sec;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author mniedre
 */
@Slf4j
@Profile("oauth2")
@RestController
public class OAuth2UserController {

    @GetMapping
    @RequestMapping("/user")
    public Principal user(@AuthenticationPrincipal OAuth2User principal) {
        return () -> principal.getAttribute("login");
    }
}
