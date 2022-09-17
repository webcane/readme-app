package cane.brothers.security.oauth2;

import cane.brothers.security.UserPrincipal;
import cane.brothers.security.oauth2.user.OAuth2UserInfo;
import cane.brothers.security.oauth2.user.OAuth2UserInfoFactory;
import cane.brothers.user.AppUser;
import cane.brothers.user.AppUserRepository;
import cane.brothers.user.AuthProvider;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author mniedre
 */
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

  private final AppUserRepository userRepository;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User oauth2User = super.loadUser(userRequest);

    try {
      return processOAuth2User(userRequest, oauth2User);
    } catch (AuthenticationException ex) {
      throw ex;
    } catch (Exception ex) {
      // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
      throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
    }
  }

  private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oauth2User) {
    OAuth2UserInfo oauth2UserInfo =
        OAuth2UserInfoFactory.getUserInfo(userRequest.getClientRegistration().getRegistrationId(),
            oauth2User.getAttributes());
    if (!StringUtils.hasLength(oauth2UserInfo.getEmail())) {
      throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
    }

    Optional<AppUser> userOptional = userRepository.findByEmail(oauth2UserInfo.getEmail());
    AppUser user;
    if (userOptional.isPresent()) {
      user = userOptional.get();
      if (!user.getProvider().equals(AuthProvider.get(userRequest.getClientRegistration().getRegistrationId()))) {
        throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
            user.getProvider() + " account. Please use your " + user.getProvider() +
            " account to login.");
      }
      user = updateExistingUser(user, oauth2UserInfo);
    } else {
      user = registerNewUser(userRequest, oauth2UserInfo);
    }

    return UserPrincipal.create(user, oauth2User.getAttributes());
  }

  private AppUser registerNewUser(OAuth2UserRequest userRequest, OAuth2UserInfo oauth2UserInfo) {
    AppUser user = new AppUser();

    user.setProvider(AuthProvider.get(userRequest.getClientRegistration().getRegistrationId()));
    user.setProviderId(oauth2UserInfo.getId());
    user.setName(oauth2UserInfo.getName());
    user.setEmail(oauth2UserInfo.getEmail());
    user.setImageUrl(oauth2UserInfo.getImageUrl());
    return userRepository.save(user);
  }

  private AppUser updateExistingUser(AppUser existingUser, OAuth2UserInfo oauth2UserInfo) {
    existingUser.setName(oauth2UserInfo.getName());
    existingUser.setImageUrl(oauth2UserInfo.getImageUrl());
    return userRepository.save(existingUser);
  }
}
