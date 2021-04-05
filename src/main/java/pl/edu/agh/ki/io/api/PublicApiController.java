package pl.edu.agh.ki.io.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.ki.io.db.PeakRepository;
import pl.edu.agh.ki.io.db.UserRepository;
import pl.edu.agh.ki.io.models.Peak;
import pl.edu.agh.ki.io.models.User;

import java.util.List;

@RestController
@RequestMapping("api/public")
public class PublicApiController {
    private UserRepository userRepository;
    private PeakRepository peakRepository;

    public PublicApiController(UserRepository userRepository, PeakRepository peakRepository) {
        this.userRepository = userRepository;
        this.peakRepository = peakRepository;
    }

    @GetMapping("users")
    public List<User> users() {
        return this.userRepository.findAll();
    }

    @GetMapping("peaks")
    public List<Peak> peaks() {
        return this.peakRepository.findAll();
    }
}
