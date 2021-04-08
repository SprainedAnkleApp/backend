package pl.edu.agh.ki.io;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pl.edu.agh.ki.io.security.JwtProperties;


@SpringBootApplication(scanBasePackages = { "pl.edu.agh.ki.io" })
@EnableConfigurationProperties(JwtProperties.class)
public class Application {
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
