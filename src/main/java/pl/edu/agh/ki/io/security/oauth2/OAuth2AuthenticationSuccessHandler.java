package pl.edu.agh.ki.io.security.oauth2;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
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
@AllArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private JwtProperties jwtProperties;
    private HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository;
    private OAuth2AuthorizedClientService clientService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Optional<String> redirectUrl = CookieUtils.getCookie(request, REDIRECT_URI_NAME).map(Cookie::getValue);

        // TODO: add validation to redirect only to allowed redirect uri's
        /*if (redirectUrl.isPresent() && !isAllowedRedirect(redirectUrl.get())){throw}*/

        String token = JwtUtils.generateToken(authentication.getName(), jwtProperties);
        String target = UriComponentsBuilder.fromUriString(redirectUrl.orElse("http://localhost:3000/"))
                .queryParam("token", token).toUriString();

        cookieAuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);

        Authentication auth =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        OAuth2AuthenticationToken oauthToken =
                (OAuth2AuthenticationToken) auth;
        OAuth2AuthorizedClient client =
                clientService.loadAuthorizedClient(
                        oauthToken.getAuthorizedClientRegistrationId(),
                        oauthToken.getName());

        String accessToken = client.getAccessToken().getTokenValue();
        logger.info(oauthToken.getAuthorizedClientRegistrationId());
        logger.info(oauthToken.getName());
        logger.info(accessToken);

        getRedirectStrategy().sendRedirect(request, response, target);
    }
}
