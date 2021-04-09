package pl.edu.agh.ki.io.security.oauth2;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpCookieOAuth2AuthorizationRequestRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    public static final String OAUTH_REQUEST_COOKIE_NAME = "oauth_auth_request";
    public static final String REDIRECT_URI_NAME = "redirect_uri";
    private static final int COOKIE_LIFETIME = 300;

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest httpServletRequest) {
        return CookieUtils.getCookie(httpServletRequest, OAUTH_REQUEST_COOKIE_NAME)
                .map(cookie -> CookieUtils.deserialize(cookie, OAuth2AuthorizationRequest.class))
                .orElse(null);
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest oAuth2AuthorizationRequest, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        if (oAuth2AuthorizationRequest == null) {
            removeAuthorizationRequestCookies(httpServletRequest, httpServletResponse);
            return;
        }

        CookieUtils.addCookie(httpServletResponse, OAUTH_REQUEST_COOKIE_NAME, CookieUtils.serialize(oAuth2AuthorizationRequest), COOKIE_LIFETIME);
        String redirectUri = httpServletRequest.getParameter(REDIRECT_URI_NAME);
        if (StringUtils.isNotBlank(redirectUri)) {
            CookieUtils.addCookie(httpServletResponse, REDIRECT_URI_NAME, redirectUri, COOKIE_LIFETIME);
        }
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
        OAuth2AuthorizationRequest oAuthRequest = this.loadAuthorizationRequest(request);
        removeAuthorizationRequestCookies(request, response);
        return oAuthRequest;
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest httpServletRequest) {
        OAuth2AuthorizationRequest oAuthRequest = this.loadAuthorizationRequest(httpServletRequest);
        CookieUtils.deleteCookie(httpServletRequest, OAUTH_REQUEST_COOKIE_NAME);
        CookieUtils.deleteCookie(httpServletRequest, REDIRECT_URI_NAME);
        return oAuthRequest;
    }

    public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.deleteCookie(request, response, OAUTH_REQUEST_COOKIE_NAME);
        CookieUtils.deleteCookie(request, response, REDIRECT_URI_NAME);
    }
}
