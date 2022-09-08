package cane.brothers.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * TODO rename to AppUserRepository
 *
 * @author mniedre
 */
@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

  Optional<AppUser> findByEmail(String email);

  Boolean existsByEmail(String email);
}
