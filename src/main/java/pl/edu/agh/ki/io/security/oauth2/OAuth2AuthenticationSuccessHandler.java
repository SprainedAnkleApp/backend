package pl.edu.agh.ki.io.security.oauth2;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import pl.edu.agh.ki.io.security.JwtProperties;
import pl.edu.agh.ki.io.security.JwtUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static pl.edu.agh.ki.io.security.JwtAuthenticationFilter.ACCESS_CONTROL_HEADER;
import static pl.edu.agh.ki.io.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_NAME;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private JwtProperties jwtProperties;
    private HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository;

    public OAuth2AuthenticationSuccessHandler(JwtProperties jwtProperties, HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository) {
        this.jwtProperties = jwtProperties;
        this.cookieAuthorizationRequestRepository = cookieAuthorizationRequestRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Optional<String> redirectUrl = CookieUtils.getCookie(request, REDIRECT_URI_NAME).map(Cookie::getValue);

        // TODO: add validation to redirect only to allowed redirect uri's
        /*if (redirectUrl.isPresent() && !isAllowedRedirect(redirectUrl.get())){throw}*/

        String token = JwtUtils.generateToken(authentication.getName(), jwtProperties);
        String target = UriComponentsBuilder.fromUriString(redirectUrl.orElse("http://localhost:3000/"))
                .queryParam("token", token).toUriString();

        cookieAuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);

        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + token);
        response.addHeader(ACCESS_CONTROL_HEADER, JwtProperties.HEADER_STRING);

        getRedirectStrategy().sendRedirect(request, response, target);
    }
}
