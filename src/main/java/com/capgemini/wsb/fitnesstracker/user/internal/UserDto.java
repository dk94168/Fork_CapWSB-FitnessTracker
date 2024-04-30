package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;

import java.time.LocalDate;

record UserDto(@Nullable Long Id, String firstName, String lastName,
               @JsonFormat(pattern = "yyyy-MM-dd") LocalDate birthdate,
               String email) {

}

record SmallUserDto(@Nullable Long Id, String firstName, String lastName) {

}

record SmallEmailUserDto(@Nullable Long Id, String email) {

}

record SmallUsersDateDto(@Nullable Long Id, String firstName, String lastName, LocalDate birthdate) {

}
