package pl.edu.agh.ki.io.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import pl.edu.agh.ki.io.db.UserStorage;
import pl.edu.agh.ki.io.models.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private UserStorage userStorage;
    private JwtProperties jwtProperties;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserStorage userStorage, JwtProperties jwtProperties) {
        super(authenticationManager);
        this.userStorage = userStorage;
        this.jwtProperties = jwtProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authHeader = request.getHeader(JwtProperties.HEADER_STRING);
        if(authHeader == null || !authHeader.startsWith(JwtProperties.TOKEN_PREFIX)){
            chain.doFilter(request, response);
            return;
        }

        Authentication authentication = getUsernamePasswordAuthentication(request, authHeader);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
    }

    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request, String token) {
        String username = JWT.require(Algorithm.HMAC512(jwtProperties.getSecret()))
                .build()
                .verify(token.replace(JwtProperties.TOKEN_PREFIX, ""))
                .getSubject();

        UserPrincipal user = (UserPrincipal) userStorage.loadUserByUsername(username);
        Authentication auth = new UsernamePasswordAuthenticationToken(username, null, user.getAuthorities());

        return auth;
    }
}
