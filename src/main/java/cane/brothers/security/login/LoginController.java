package cane.brothers.security.login;

import static cane.brothers.security.SecurityConfig.DEFAULT_LOGIN_REDIRECT_URI;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoginController {

  @RequestMapping(value = "/login")
  public void loginRedirect(HttpServletResponse response) throws IOException {
    response.sendRedirect("/oauth2/authorization/github");
  }

  @RequestMapping(value = DEFAULT_LOGIN_REDIRECT_URI)
  // @CurrentUser UserPrincipal userPrincipal
  public void loginToken() {
    //log.info("login " + userPrincipal.getName());
  }

  @GetMapping("/favicon.ico")
  void returnNoFavicon() {
    // gracefully disable favicon
  }
}
