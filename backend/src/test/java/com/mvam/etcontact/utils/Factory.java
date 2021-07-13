package com.mvam.etcontact.utils;

import com.github.javafaker.Faker;
import com.mvam.etcontact.dto.*;
import com.mvam.etcontact.entities.Role;
import com.mvam.etcontact.entities.User;

import java.time.ZoneId;
import java.util.Collections;
import java.util.HashSet;

public class Factory {

    private static final Faker FAKER = Faker.instance();

    public static User createUser(Long id) {
        return User.builder()
                .id(id)
                .name(FAKER.dragonBall().character())
                .birthDate(FAKER.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .cpf("012.123.234-25")
                .email("fulano@gmail.com")
                .password("fulano")
                .roles(new HashSet<>())
                .contacts(new HashSet<>())
                .build();
    }

    public static UserDTO createUserDTO(Long id) {
        return UserDTO.builder()
                .id(id)
                .name(FAKER.dragonBall().character())
                .birthDate(FAKER.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .cpf("012.123.234-25")
                .email("fulano@gmail.com")
                .build();
    }

    public static UserInsertDTO createUserInsertDTO(Long id) {
        return UserInsertDTO.builder()
                .id(id)
                .name(FAKER.dragonBall().character())
                .birthDate(FAKER.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .cpf("012.123.234-25")
                .email("fulano@gmail.com")
                .password("fulano")
                .roles(new HashSet<>(Collections.singletonList(createRoleDTO(1L))))
                .contacts(new HashSet<>(Collections.singletonList(createContactDTO())))
                .build();
    }

    public static UserUpdateDTO createUserUpdateDTO(Long id) {
        return UserUpdateDTO.builder()
                .id(id)
                .name(FAKER.dragonBall().character())
                .birthDate(FAKER.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .cpf("012.123.234-25")
                .email("fulano@gmail.com")
                .password("fulano")
                .roles(new HashSet<>(Collections.singletonList(createRoleDTO(1L))))
                .contacts(new HashSet<>(Collections.singletonList(createContactDTO())))
                .build();
    }

    public static Role createRole(Long id) {
        return Role.builder()
                .id(id)
                .authority("ROLE_VISITOR")
                .build();
    }

    public static RoleDTO createRoleDTO(Long id) {
        return RoleDTO.builder()
                .id(id)
                .authority("ROLE_VISITOR")
                .build();
    }

    public static ContactDTO createContactDTO() {
        return ContactDTO.builder()
                .name(FAKER.dragonBall().character())
                .phone("44 3333-1111")
                .email("fulano@gmail.com")
                .build();
    }
}
