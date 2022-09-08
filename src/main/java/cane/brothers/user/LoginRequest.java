package cane.brothers.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mniedre
 */
@Data
@NoArgsConstructor
public class LoginRequest {

  @NotBlank
  @Email
  private String email;

  @NotBlank
  private String password;
}
