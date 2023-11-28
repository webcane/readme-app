package cane.brothers.security.appuser;

import cane.brothers.exception.ResourceNotFoundException;
import cane.brothers.security.oauth2.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author mniedre
 */
@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

  private final AppUserRepository userRepository;

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String email)
      throws UsernameNotFoundException {
    return userRepository.findByEmail(email)
        .map(UserPrincipal::create)
        .orElseThrow(() ->
            new UsernameNotFoundException("AppUser not found by email: " + email)
        );
  }

  @Transactional(readOnly = true)
  public UserDetails loadUserById(Long id) {
    return userRepository.findById(id)
        .map(UserPrincipal::create)
        .orElseThrow(
        () -> new ResourceNotFoundException("User", "id", id)
    );
  }
}
