package pl.edu.agh.ki.io;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pl.edu.agh.ki.io.security.JwtProperties;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication(scanBasePackages = { "pl.edu.agh.ki.io" })
@EnableConfigurationProperties(JwtProperties.class)
@EnableSwagger2
public class Application {
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
