package pl.edu.agh.ki.io.security.oauth2;


import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import pl.edu.agh.ki.io.db.UserRepository;
import pl.edu.agh.ki.io.models.AuthProvider;
import pl.edu.agh.ki.io.models.User;
import pl.edu.agh.ki.io.security.AuthenticationProcessingException;
import pl.edu.agh.ki.io.security.oauth2.userinfo.OAuth2UserInfo;
import pl.edu.agh.ki.io.security.oauth2.userinfo.OAuth2UserInfoFactory;
import pl.edu.agh.ki.io.security.UserPrincipal;

import java.util.Optional;

@Service
public class OAuth2UserStorage extends DefaultOAuth2UserService {

    private UserRepository userRepository;

    public OAuth2UserStorage(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        try {
            return saveOrUpdateOAuthUser(userRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User saveOrUpdateOAuthUser(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.build(registrationId, oAuth2User.getAttributes());
        // StringUtils.isEmpty is deprecated.
        if(ObjectUtils.isEmpty(userInfo.getEmail())) {
            throw new AuthenticationProcessingException("Email from provider is required to continue");
        }

        Optional<User> userOptional = userRepository.findUserByEmail(userInfo.getEmail());
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            if (!user.getAuthProvider().equals(AuthProvider.valueOf(registrationId))) {
                throw new AuthenticationProcessingException("User signed up with " + registrationId);
            }
        } else {
            user = userInfo.toUser();
            userRepository.save(user);
        }

        return new UserPrincipal(user, oAuth2User.getAttributes());
    }
}
