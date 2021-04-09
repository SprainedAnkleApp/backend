package pl.edu.agh.ki.io.oauth2.userinfo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

// TODO: Refactor. Move UseDetails from User to UserPrincipal.
public class UserPrincipal implements OAuth2User {
    private Map<String, Object> attributes;

    public UserPrincipal(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new HashSet<>();
    }

    @Override
    public String getName() {
        return (String) attributes.get("email");
    }
}
