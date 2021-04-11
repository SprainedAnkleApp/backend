package pl.edu.agh.ki.io.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

public class JwtUtils {
    public static String generateToken(String name, JwtProperties jwtProperties) {
        return JWT.create()
                .withSubject(name)
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getExpirationTime()))
                .sign(Algorithm.HMAC512(jwtProperties.getSecret()));
    }
}
