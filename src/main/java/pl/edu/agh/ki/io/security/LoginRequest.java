package pl.edu.agh.ki.io.security;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
