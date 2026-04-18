package team10.group_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import team10.group_app.domain.User;
import team10.group_app.dto.UserDTO;
import team10.group_app.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping({ "/", "" })
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO user) {
        User createdUser = userService.createUser(user.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser.toDTO());
    }

    @GetMapping({ "/", "" })
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> users = userService.getUsers().stream().map(User::toDTO).toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping({ "/{id}" })
    public ResponseEntity<UserDTO> getUser(@PathVariable int id) {
        return ResponseEntity.ok(userService.getUserById(id).toDTO());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable int id, @RequestBody UserDTO updatedUser) {
        return ResponseEntity.ok(userService.updateRole(id, updatedUser.getRole()).toDTO());
    }
}
