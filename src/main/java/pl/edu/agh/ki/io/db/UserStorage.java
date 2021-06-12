package pl.edu.agh.ki.io.db;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.agh.ki.io.models.PageParameters;
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

    public UserDetails createUser(User user) throws AuthenticationProcessingException {
        Optional<User> optionalUser = userRepository.findUserByEmail(user.getEmail());
        if (optionalUser.isPresent()){
            throw new AuthenticationProcessingException("User already registered with " + optionalUser.get().getAuthProvider());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        try {
            user = userRepository.save(user);
        } catch (Exception e) {
            throw new AuthenticationProcessingException("Conflicting login or email");
        }
        
        return new UserPrincipal(user);
    }

    public User saveUser(User user) {
        return this.userRepository.save(user);
    }

    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    public Page<User> findAll(PageParameters pageParameters) {
        Sort sort = Sort.by(pageParameters.getSortDirection(), pageParameters.getSortBy());

        Pageable pageable = PageRequest.of(pageParameters.getPageNumber(),
                pageParameters.getPageSize(), sort);
        return this.userRepository.findAll(pageable);
    }

    public Page<User> findBySearchTerm(String searchTerm, PageParameters pageParameters) {
        Pageable pageable = PageRequest.of(pageParameters.getPageNumber(),
                pageParameters.getPageSize());
        return this.userRepository.findBySearchTerm(searchTerm, pageable);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = this.userRepository.findUserByLogin(s).orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", s)));
        return new UserPrincipal(user);
    }

    public Optional<User> findUserByFacebookId(String facebookId) {
        return this.userRepository.findUserByFacebookUserId(facebookId);
    }

    public Optional<User> findUserById(Long userId) {
        return this.userRepository.findById(userId);
    }

}
