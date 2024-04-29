package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    @GetMapping("/fullUsers")
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                          .stream()
                          .map(userMapper::toDto)
                          .toList();
    }

    //@GetMapping("/byId/{id}")
    //public List<UserDto> getUserById() {
    //    return userService.findAllUsers()
    //            .stream()
    //            .map(userMapper::toDto)
    //            .toList();
    //}

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

    @GetMapping("/smallUsers")
    public List<SmallUserDto> getSmallAllUsers() {
        return userService.findSmallAllUsers()
                .stream()
                .map(userMapper::smallToDto)
                .toList();
    }

    @PostMapping
    public User addUser(@RequestBody UserDto userDto) {

        // Demonstracja how to use @RequestBody
        System.out.println("User with e-mail: " + userDto.email() + "passed to the request");

        // TODO: saveUser with Service and return User
        return null;
    }

}