package pl.edu.agh.ki.io.security;

public class JwtProperties  {
    public static final String SECRET = "super_secret"; //TODO: store somewhere not hear :)
    public static final int EXPIRATION_TIME = 10 * 24 * 60 * 60 * 1000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
