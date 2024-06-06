package de.hska.iwi.EShop.user.api;

import de.hska.iwi.EShop.user.persistence.User;
import de.hska.iwi.EShop.user.service.RoleService;
import de.hska.iwi.EShop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {
    private final UserService userService;
    private final RoleService roleService;

    @Override
    public ResponseEntity<UserDTO> createNewUser(CreateNewUserRequestDTO createNewUserRequestDTO) {
        if (userService.doesUserAlreadyExist(createNewUserRequestDTO.getUserName())) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(412));
        }

        final User user = userService.registerUser(
                createNewUserRequestDTO.getUserName(),
                createNewUserRequestDTO.getFirstName(),
                createNewUserRequestDTO.getLastName(),
                createNewUserRequestDTO.getPassword(),
                roleService.getRegularUserRole()
        );

        return ResponseEntity.ok(UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstname())
                .lastName(user.getLastname())
                .name(user.getUsername())
                .password(user.getPassword())
                .role(RoleDTO.builder()
                        .id(user.getRole().getId())
                        .level(user.getRole().getRoleLevel())
                        .typ(user.getRole().getRoleType())
                        .build())
                .build());
    }

    @Override
    public ResponseEntity<Void> deleteUserById(Integer id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<UserDTO> getUserById(Integer id) {
        return userService.getUserById(id)
                .map(user -> ResponseEntity.ok(UserDTO.builder()
                        .id(user.getId())
                        .firstName(user.getFirstname())
                        .lastName(user.getLastname())
                        .name(user.getUsername())
                        .password(user.getPassword())
                        .role(RoleDTO.builder()
                                .id(user.getRole().getId())
                                .level(user.getRole().getRoleLevel())
                                .typ(user.getRole().getRoleType())
                                .build())
                        .build()))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<UserDTO> getUserByName(GetUserByNameRequestDTO getUserByNameRequestDTO) {
        return userService.getUserByUsername(getUserByNameRequestDTO.getUserName())
                .map(user -> ResponseEntity.ok(UserDTO.builder()
                        .id(user.getId())
                        .firstName(user.getFirstname())
                        .lastName(user.getLastname())
                        .name(user.getUsername())
                        .password(user.getPassword())
                        .role(RoleDTO.builder()
                                .id(user.getRole().getId())
                                .level(user.getRole().getRoleLevel())
                                .typ(user.getRole().getRoleType())
                                .build())
                        .build()))
                .orElse(ResponseEntity.notFound().build());
    }
}
