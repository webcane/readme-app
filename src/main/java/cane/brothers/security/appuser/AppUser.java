package cane.brothers.security.appuser;

import cane.brothers.security.oauth2.user.AuthProvider;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;


/**
 *
 * @author mniedre
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "USERS", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class AppUser implements Serializable, Persistable<Long> {

    private static final long serialVersionUID = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Email
    @Column(nullable = false)
    private String email;

    private String imageUrl;

    @Column(nullable = false)
    private Boolean emailVerified = false;

    @JsonIgnore
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

    @Override
    public boolean isNew() {
        return id == null;
    }
}
