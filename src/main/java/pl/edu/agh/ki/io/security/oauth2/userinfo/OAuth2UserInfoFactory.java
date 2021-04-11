package pl.edu.agh.ki.io.security.oauth2.userinfo;

import pl.edu.agh.ki.io.models.AuthProvider;
import pl.edu.agh.ki.io.security.AuthenticationProcessingException;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo build(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(AuthProvider.google.toString()))
            return new GoogleOAuth2UserInfo(attributes);
        else if(registrationId.equalsIgnoreCase(AuthProvider.facebook.toString()))
            return new FacebookOAuth2UserInfo(attributes);
        else
            throw new AuthenticationProcessingException("Unknown provider " + registrationId);
    }
}
