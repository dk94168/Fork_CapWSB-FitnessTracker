package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UserMapper {

     public UserDto toDto(User user) {
        return new UserDto(user.getId(),
                           user.getFirstName(),
                           user.getLastName(),
                           user.getBirthdate(),
                           user.getEmail());
    }

    SmallUserDto smallToDto(User user) {
        return new SmallUserDto(user.getId(),
                            user.getFirstName(),
                            user.getLastName());
    }

    SmallEmailUserDto smallEmailToDto(User user) {
        return new SmallEmailUserDto(user.getId(),
                            user.getEmail());
    }

    SmallUsersDateDto smallUsersDateDto(User user) {
        return new SmallUsersDateDto(user.getId(),
                            user.getFirstName(),
                            user.getLastName(),
                            user.getBirthdate());
    }

    User toEntity(UserDto userDto) {
        return new User(
                        userDto.firstName(),
                        userDto.lastName(),
                        userDto.birthdate(),
                        userDto.email());
    }

}
