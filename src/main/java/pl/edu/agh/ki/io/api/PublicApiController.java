package pl.edu.agh.ki.io.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.ki.io.db.UserRepository;
import pl.edu.agh.ki.io.models.User;

import java.util.List;

@RestController
@RequestMapping("api/public")
public class PublicApiController {
    private UserRepository userRepository;

    public PublicApiController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("users")
    public List<User> users() {
        return this.userRepository.findAll();
    }
}
