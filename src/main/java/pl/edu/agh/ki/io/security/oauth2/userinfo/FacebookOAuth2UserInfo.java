package pl.edu.agh.ki.io.security.oauth2.userinfo;

import pl.edu.agh.ki.io.models.AuthProvider;
import pl.edu.agh.ki.io.models.User;

import java.util.Map;

public class FacebookOAuth2UserInfo extends OAuth2UserInfo {
    public FacebookOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
        authProvider = AuthProvider.facebook;
    }

    @Override
    public String getId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getFirstName() {
        return ((String) attributes.get("name")).split(" ")[0];
    }

    @Override
    public String getLastName() {
        return ((String) attributes.get("name")).split(" ")[1];
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getImageUrl() {
        if(attributes.containsKey("picture")) {
            Map<String, Object> pictureObj = (Map<String, Object>) attributes.get("picture");
            if(pictureObj.containsKey("data")) {
                Map<String, Object>  dataObj = (Map<String, Object>) pictureObj.get("data");
                if(dataObj.containsKey("url")) {
                    return (String) dataObj.get("url");
                }
            }
        }
        return null;
    }

    @Override
    public User toUser() {
        User user = super.toUser();
        user.setFacebookUserId(getId());
        return user;
    }
}
