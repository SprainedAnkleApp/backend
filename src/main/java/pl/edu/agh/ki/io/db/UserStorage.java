package pl.edu.agh.ki.io.db;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.agh.ki.io.models.User;
import pl.edu.agh.ki.io.security.AuthenticationProcessingException;
import pl.edu.agh.ki.io.security.UserPrincipal;

import java.util.List;
import java.util.Optional;

@Service
public class UserStorage implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserStorage(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDetails createUser(User user) {
        Optional<User> optionalUser = userRepository.findUserByEmail(user.getEmail());
        if (optionalUser.isPresent()){
            throw new AuthenticationProcessingException("User already registered with " + optionalUser.get().getAuthProvider());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return new UserPrincipal(userRepository.save(user));
    }

    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = this.userRepository.findUserByLogin(s).orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", s)));
        return new UserPrincipal(user);
    }
}
