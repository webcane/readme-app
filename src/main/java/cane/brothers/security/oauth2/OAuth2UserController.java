package cane.brothers.security.oauth2;


import cane.brothers.exception.ResourceNotFoundException;
import cane.brothers.security.appuser.AppUser;
import cane.brothers.security.appuser.AppUserRepository;
import cane.brothers.security.oauth2.user.CurrentUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mniedre
 */
@Slf4j
@Profile("oauth2")
@RestController
public class OAuth2UserController {

  @Autowired
  private AppUserRepository userRepository;

//    @GetMapping
//    @RequestMapping("/user")
//    public Principal user(@AuthenticationPrincipal OAuth2User principal) {
//        return () -> principal.getAttribute("login");
//    }


  @GetMapping("/api/user")
  @PreAuthorize("hasRole('USER')")
  public AppUser getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
    return userRepository.findById(userPrincipal.getId())
        .orElseThrow(() -> new ResourceNotFoundException("AppUser", "id", userPrincipal.getId()));
  }
}
