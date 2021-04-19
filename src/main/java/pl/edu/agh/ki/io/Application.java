package pl.edu.agh.ki.io;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import pl.edu.agh.ki.io.security.JwtProperties;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@SpringBootApplication(scanBasePackages = { "pl.edu.agh.ki.io" })
@EnableConfigurationProperties(JwtProperties.class)
@EnableSwagger2
public class Application {
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
