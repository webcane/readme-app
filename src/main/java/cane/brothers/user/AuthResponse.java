package cane.brothers.user;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mniedre
 */
@Data
@NoArgsConstructor
public class AuthResponse {

  private String accessToken;

  private String tokenType = "Bearer";

  public AuthResponse(String accessToken) {
    this.accessToken = accessToken;
  }
}
