package pl.edu.agh.ki.io.db;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.agh.ki.io.models.User;

@Service
public class UserStorage implements UserDetailsService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserStorage(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createOrUpdateUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.userRepository.findUserByLogin(s).orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", s)));
    }
}
