package jetbrains.recipes.contoller;

import jetbrains.recipes.model.User;
import jetbrains.recipes.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserController(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @PostMapping("/api/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
