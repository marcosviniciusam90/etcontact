package com.mvam.etcontact.dto;

import com.mvam.etcontact.services.validation.UserUpdateValid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

@UserUpdateValid
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor()
public class UserUpdateDTO extends UserDTO {
    @NotBlank
    private String password;
}
