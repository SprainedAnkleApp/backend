package pl.edu.agh.ki.io.security;

import org.springframework.security.core.AuthenticationException;

public class AuthenticationProcessingException extends AuthenticationException {
    public AuthenticationProcessingException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public AuthenticationProcessingException(String msg) {
        super(msg);
    }
}
