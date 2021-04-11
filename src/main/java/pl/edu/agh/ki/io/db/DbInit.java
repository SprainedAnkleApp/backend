package pl.edu.agh.ki.io.db;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.agh.ki.io.models.AuthProvider;
import pl.edu.agh.ki.io.models.Gender;
import pl.edu.agh.ki.io.models.Peak;
import pl.edu.agh.ki.io.models.User;
import pl.edu.agh.ki.io.models.wallElements.Photo;
import pl.edu.agh.ki.io.models.wallElements.Post;

import java.sql.Date;


@Service
public class DbInit implements CommandLineRunner {
    private UserRepository userRepository;
    private GenderRepository genderRepository;
    private PasswordEncoder passwordEncoder;
    private PeakRepository peakRepository;
    private WallItemRepository wallItemRepository;

    public DbInit(UserRepository userRepository, GenderRepository genderRepository, PasswordEncoder passwordEncoder, PeakRepository peakRepository, WallItemRepository wallItemRepository) {
        this.userRepository = userRepository;
        this.genderRepository = genderRepository;
        this.passwordEncoder = passwordEncoder;
        this.peakRepository = peakRepository;
        this.wallItemRepository = wallItemRepository;
    }

    @Override
    public void run(String... args) {
        this.wallItemRepository.deleteAll();
        this.userRepository.deleteAll();
        this.genderRepository.deleteAll();
        this.peakRepository.deleteAll();

        Gender male = new Gender("Male");
        this.genderRepository.save(male);

        Date birthday = Date.valueOf("2000-12-1");
        User testUser = new User("admin", passwordEncoder.encode("admin"), AuthProvider.local, "Test",
                "Testowski", "test1@mail.com", male, "https://i.imgur.com/VNNp6zWb.jpg", birthday, "+48880053535");

        userRepository.save(testUser);

        Peak testPeak = new Peak("Rysy", 2499, "małopolskie", "Góra położona na granicy polsko-słowackiej, w Tatrach Wysokich (jednej z części Tatr). Ma trzy wierzchołki, z których najwyższy jest środkowy (2501 metrów nad poziomem morza), znajdujący się w całości na terytorium Słowacji. Wierzchołek północno-zachodni, przez który biegnie granica, stanowi najwyżej położony punkt Polski i należy do Korony Europy.", "Tatry", "https://images.unsplash.com/photo-1562878716-48b7542721e3?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80");
        peakRepository.save(testPeak);

        Peak testPeak2 = new Peak("Babia Góra", 1725, "małopolskie", "Masyw górski w Paśmie Babiogórskim należącym do Beskidu Żywieckiego w Beskidach Zachodnich. Jest najwyższym szczytem Beskidów Zachodnich i poza Tatrami najwyższym szczytem w Polsce, drugim co do wybitności (po Śnieżce).", "Beskid Żywiecki", "https://images.unsplash.com/photo-1614773305142-260aa1658982?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1782&q=80");
        peakRepository.save(testPeak2);

        Peak testPeak3 = new Peak("Śnieżka", 1603, "dolnośląskie", "Najwyższy szczyt Karkonoszy oraz Sudetów, jak również Czech, województwa dolnośląskiego, a także całego Śląska. Najwybitniejszy szczyt Polski i Czech.", "Karkonosze", "https://images.unsplash.com/photo-1615124977398-08f4b1f678fe?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1650&q=80");
        peakRepository.save(testPeak3);

        Photo photo = new Photo(testUser, "content", "photopath");
        wallItemRepository.save(photo);

        Post post = new Post(testUser, "content");
        wallItemRepository.save(post);

    }
}
