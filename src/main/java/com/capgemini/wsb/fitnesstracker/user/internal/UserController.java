package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    @DeleteMapping("/removeUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("Użytkownik został usunięty, ID: " + id);
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/userUpdate/{userId}")
    public ResponseEntity<Void> updateUser(@PathVariable Long userId, @RequestBody User updateUser) {
        try {
            userService.updateUser(userId, updateUser);
            return ResponseEntity.noContent().build();
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/addUser")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        try {
            return ResponseEntity.ok(userService.addUser(user));
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/fullUsers")
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @GetMapping("/byId/{id}")
    public Optional<UserDto> getUserById(@PathVariable Long id) {
        return userService.getUser(id)
                .stream()
                .map(userMapper::toDto)
                .findFirst();
    }

    @GetMapping("/byEmail/{email}")
    public Optional<UserDto> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email)
                .map(userMapper::toDto);
    }

    @GetMapping("/byEmailIgnore/{email}")
    public Optional<SmallEmailUserDto> getUserByEmailIgnore(@PathVariable String email) {
        return userService.getUserByEmailIgnore(email)
                .map(userMapper::smallEmailToDto);
    }

    @GetMapping("/byEmailSearch/{email}")
    public Optional<SmallEmailUserDto> getUserByEmailSearch(@PathVariable String email) {
        return userService.findUserByEmail(email)
                .map(userMapper::smallEmailToDto);
    }

    @GetMapping("/byDateSearch/{birthdate}")
    public List<SmallUsersDateDto> findUsersByDate(@PathVariable LocalDate birthdate) {
        return userService.findUsersByBirthdate(birthdate)
                .stream()
                .map(userMapper::smallUsersDateDto)
                .toList();
    }

    @GetMapping("/smallUsers")
    public List<SmallUserDto> getSmallAllUsers() {
        return userService.findSmallAllUsers()
                .stream()
                .map(userMapper::smallToDto)
                .toList();
    }

    //@PostMapping
    //public User addUser(@RequestBody UserDto userDto) {
    //
    // Demonstracja how to use @RequestBody
    //    System.out.println("User with e-mail: " + userDto.email() + "passed to the request");
    //
    // TODO: saveUser with Service and return User
    //    return null;
    //}

}