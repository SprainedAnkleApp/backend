package pl.edu.agh.ki.io;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.ki.io.db.UserRepository;
import pl.edu.agh.ki.io.models.User;

import java.util.List;


@RestController
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("secret")
    public String secret() {
        return "secret";
    }

}
