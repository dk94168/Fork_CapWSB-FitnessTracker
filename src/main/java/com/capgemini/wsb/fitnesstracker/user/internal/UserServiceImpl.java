package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import jakarta.persistence.CascadeType;

@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    @Autowired
    private final UserRepository userRepository;

    @Override
    public User createUser(final User user) {
        log.info("Creating User {}", user);
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User userDelete = optionalUser.get();
            userRepository.delete(userDelete);
            log.info("Deleting User with ID {}", userId);
        }
        else {
            throw new IllegalArgumentException("User with ID " + userId + " not found!");
        }

    }

    @Override
    public void updateUser(Long userId, User updateUser) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (updateUser.getFirstName() != null) {
                user.setFirstName(updateUser.getFirstName());
            }

            if (updateUser.getLastName() != null) {
                user.setLastName(updateUser.getLastName());
            }

            if (updateUser.getEmail() != null) {
                user.setEmail(updateUser.getEmail());
            }

            if (updateUser.getBirthdate() != null) {
                user.setBirthdate(updateUser.getBirthdate());
            }

            log.info("Update User Data with ID {}", userId);
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User not found with id: " + userId);
        }
    }

    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> getUserByEmailIgnore(final String email) {
        return userRepository.findByEmailIgnore(email);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findSmallAllUsers() { return userRepository.findAll(); }

    @Override
    public Optional<User> findUserByEmail(final String email) { return userRepository.findByEmailSearch(email); }

    @Override
    public List<User> findUsersByBirthdate(LocalDate birthdate) { return userRepository.findUsersByDate(birthdate); }

}