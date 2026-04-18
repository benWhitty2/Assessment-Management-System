package team10.group_app.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;
import team10.group_app.domain.SecurityUser;
import team10.group_app.domain.User;
import team10.group_app.dto.TokenDTO;
import team10.group_app.dto.UserSignupDTO;
import team10.group_app.repository.UserRepository;

@Service
@Transactional
public class UserService {

    private static final String USER_NOT_FOUND = "User does not exist.";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JpaUserDetailsService detailsService;
    private final TokenService tokenService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
            JpaUserDetailsService jpaUserDetailsService, TokenService tokenService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.detailsService = jpaUserDetailsService;
        this.tokenService = tokenService;
    }

    public User createUser(User newUser) {
        return userRepository.save(newUser);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOT_FOUND));
    }

    public void deleteUser(int id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOT_FOUND);
        }
        userRepository.deleteById(id);
    }

    public User updateRole(int id, String role) {
        User storedUser = getUserById(id);
        storedUser.setRole(role);
        return userRepository.save(storedUser);
    }

    public User authenticate(String email, String password) {
        return userRepository.findAll().stream()
                .filter(u -> u.getEmail().equals(email) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    public static final String ROLE_USER = "ROLE_USER";

    public TokenDTO signupNewUser(UserSignupDTO userSignupDTO) {
        User newUser = new User(
                userSignupDTO.getUsername(),
                passwordEncoder.encode(userSignupDTO.getPassword()),
                userSignupDTO.getRole());
        newUser.setEmail(userSignupDTO.getEmail());
        // Set required defaults to satisfy non-null constraints
        newUser.setActive(true);
        userRepository.save(newUser);
        SecurityUser securityUser = (SecurityUser) detailsService.loadUserByUsername(newUser.getUsername());
        return new TokenDTO(tokenService.generateToken(securityUser.getAuthorities(), newUser.getUsername()),
                newUser.toDTO());
    }

    public TokenDTO loginUser(Authentication authentication) {
        String username = authentication.getName().toLowerCase();

        User authedUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOT_FOUND));

        return new TokenDTO(
                tokenService.generateToken(authentication.getAuthorities(), authedUser.getUsername()),
                authedUser.toDTO());
    }

}
