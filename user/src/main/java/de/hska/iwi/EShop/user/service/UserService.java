package de.hska.iwi.EShop.user.service;

import de.hska.iwi.EShop.user.persistence.Role;
import de.hska.iwi.EShop.user.persistence.User;
import de.hska.iwi.EShop.user.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User registerUser(String username, String name, String lastname, String password, Role role) {
        User user = new User(username, name, lastname, password, role);
        return userRepository.save(user);
    }

    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    public boolean doesUserAlreadyExist(String username) {
        Optional<User> user = this.getUserByUsername(username);

        return user.isPresent();
    }

    public boolean validatePassword(String password1, String password2){
        return Objects.equals(password1, password2);
    }
}
