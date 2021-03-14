package cane.brothers.user;

import org.springframework.util.Assert;

import java.util.Objects;

/**
 * @author mniedre
 */
public enum AuthProvider {
    LOCAL,
    GITHUB;
//    GOOGLE,
//    FACEBOOK

    public static AuthProvider get(String provider) {
        Assert.notNull(provider, "Authentication provider must not be null");
        return AuthProvider.valueOf(provider.toUpperCase());
    }
}
