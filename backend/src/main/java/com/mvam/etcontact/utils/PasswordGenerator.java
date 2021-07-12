package com.mvam.etcontact.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {

    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String value = "123456";
        String encodedValue = "$2a$10$4iGKD1zgonKM7HHVem1GT.fAtrTUbQNJptAJhlsoz6ya2IY9cCSJW";

        System.out.println(String.format("%s -> %s", value, passwordEncoder.encode(value)));
        System.out.println(String.format("%s == %s ? %s", value, encodedValue,
                passwordEncoder.matches(value, encodedValue)));

    }
}
