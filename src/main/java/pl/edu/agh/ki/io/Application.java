package pl.edu.agh.ki.io;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "pl.edu.agh.ki.io" })
public class Application {
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
