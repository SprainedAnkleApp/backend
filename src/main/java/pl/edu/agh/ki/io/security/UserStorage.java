package pl.edu.agh.ki.io.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.edu.agh.ki.io.db.UserRepository;
import pl.edu.agh.ki.io.models.User;

import java.util.Optional;

@Service
public class UserStorage implements UserDetailsService {
    private UserRepository userRepository;

    public UserStorage(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.userRepository.findUserByLogin(s).orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", s)));
    }
}
