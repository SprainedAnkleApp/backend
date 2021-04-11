package pl.edu.agh.ki.io.security.oauth2.userinfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.ki.io.models.AuthProvider;
import pl.edu.agh.ki.io.models.User;

import java.util.Map;

public abstract class OAuth2UserInfo {
    protected Map<String, Object> attributes;
    protected AuthProvider authProvider;

    Logger logger = LoggerFactory.getLogger(OAuth2UserInfo.class);

    public OAuth2UserInfo(Map<String, Object> attributes) {
        logger.info(attributes.toString());
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getId();

    public abstract String getFirstName();

    public abstract String getLastName();

    public abstract String getEmail();

    public abstract String getImageUrl();

    public User toUser() {
        return new User(
                getEmail(),
                null,
                authProvider,
                getFirstName(),
                getLastName(),
                getEmail()
        );
    }
}
