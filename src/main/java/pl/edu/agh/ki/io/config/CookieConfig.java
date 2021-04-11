package pl.edu.agh.ki.io.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.agh.ki.io.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;

@Configuration
public class CookieConfig {
    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }
}
