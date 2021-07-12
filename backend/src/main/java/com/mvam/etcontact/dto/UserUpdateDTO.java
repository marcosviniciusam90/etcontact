package com.mvam.etcontact.dto;

import com.mvam.etcontact.services.validation.UserUpdateValid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@UserUpdateValid
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor()
public class UserUpdateDTO extends UserDTO {
    private String password;
}
