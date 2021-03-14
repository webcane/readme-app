package cane.brothers.security;

import cane.brothers.exception.ResourceNotFoundException;
import cane.brothers.user.AppUser;
import cane.brothers.user.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author mniedre
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    AppUserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("AppUser not found with email : " + email)
                );

        // TODO delegate
        return UserPrincipal.create(user);
        // return new AppUser(applicationUser.getUsername(), applicationUser.getPassword(), emptyList());
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        AppUser user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );

        // TODO delegate
        return UserPrincipal.create(user);
    }
}
