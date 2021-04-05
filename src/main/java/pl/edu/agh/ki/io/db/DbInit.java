package pl.edu.agh.ki.io.db;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.agh.ki.io.models.Gender;
import pl.edu.agh.ki.io.models.Peak;
import pl.edu.agh.ki.io.models.User;

import java.sql.Date;


@Service
public class DbInit implements CommandLineRunner {
    private UserRepository userRepository;
    private GenderRepository genderRepository;
    private PasswordEncoder passwordEncoder;
    private PeakRepository peakRepository;

    public DbInit(UserRepository userRepository, GenderRepository genderRepository, PasswordEncoder passwordEncoder, PeakRepository peakRepository) {
        this.userRepository = userRepository;
        this.genderRepository = genderRepository;
        this.passwordEncoder = passwordEncoder;
        this.peakRepository = peakRepository;
    }

    @Override
    public void run(String... args) {
        this.userRepository.deleteAll();
        this.genderRepository.deleteAll();
        this.peakRepository.deleteAll();

        Gender male = new Gender("Male");
        this.genderRepository.save(male);

        Date birthday = Date.valueOf("2000-12-1");
        User testUser = new User("admin", passwordEncoder.encode("admin"), "Test",
                "Testowski", "test1@mail.com", "https://i.imgur.com/VNNp6zWb.jpg", birthday, male, "+48880053535");

        userRepository.save(testUser);

        Peak testPeak = new Peak("Rysy", 2499, "małopolskie", "Góra położona na granicy polsko-słowackiej, w Tatrach Wysokich (jednej z części Tatr).", "Tatry", "https://www.pinterest.com/pin/78250112250865517/");
        peakRepository.save(testPeak);

        Peak testPeak2 = new Peak("Babia Góra", 1725, "małopolskie", "Masyw górski w Paśmie Babiogórskim należącym do Beskidu Żywieckiego w Beskidach Zachodnich", "Beskid Żywiecki", "https://www.pinterest.com/pin/78250112250865517/");
        peakRepository.save(testPeak2);

        Peak testPeak3 = new Peak("Śnieżka", 1603, "dolnośląskie", "Najwyższy szczyt Karkonoszy oraz Sudetów, jak również Czech.", "Karkonosze", "https://www.pinterest.com/pin/78250112250865517/");
        peakRepository.save(testPeak3);
    }
}
