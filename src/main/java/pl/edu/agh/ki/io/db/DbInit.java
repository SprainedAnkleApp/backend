package pl.edu.agh.ki.io.db;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.agh.ki.io.models.Gender;
import pl.edu.agh.ki.io.models.User;

import java.sql.Date;


@Service
public class DbInit implements CommandLineRunner {
    private UserRepository userRepository;
    private GenderRepository genderRepository;
    private PasswordEncoder passwordEncoder;

    public DbInit(UserRepository userRepository, GenderRepository genderRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.genderRepository = genderRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        this.userRepository.deleteAll();
        this.genderRepository.deleteAll();

        Gender male = new Gender("Male");
        this.genderRepository.save(male);

        Date birthday = Date.valueOf("2000-12-1");
        User testUser = new User("admin", passwordEncoder.encode("admin"), "Test",
                "Testowski", "test1@mail.com", "https://i.imgur.com/VNNp6zWb.jpg", birthday, male, "+48880053535");

        userRepository.save(testUser);
    }
}
