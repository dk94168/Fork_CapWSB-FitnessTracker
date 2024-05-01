package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Objects;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;

interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Query searching users by email address. It matches by exact match.
     *
     * @param email email of the user to search
     * @return {@link Optional} containing found user or {@link Optional#empty()} if none matched
     */

    //void deleteUserById(Long id);

    default Optional<User> findByEmail(String email) {
        return findAll().stream()
                        .filter(user -> Objects.equals(user.getEmail(), email))
                        .findFirst();
    }

    default Optional<User> findByEmailIgnore(String email) {
        return findAll().stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    default Optional<User> findByEmailSearch(String email) {
        return findAll().stream()
                .filter(user -> user.getEmail().toLowerCase().contains(email.toLowerCase()))
                .findFirst();
    }

    default List<User> findUsersByDate(LocalDate birthdate) {
        return findAll().stream()
                .filter(user -> user.getBirthdate().isBefore(birthdate))
                .collect(Collectors.toList());
    }

    void delete(User user);

}
