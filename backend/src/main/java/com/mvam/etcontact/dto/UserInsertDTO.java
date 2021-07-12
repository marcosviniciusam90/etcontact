package com.mvam.etcontact.dto;

import com.mvam.etcontact.services.validation.UserInsertValid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

@UserInsertValid
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor()
public class UserInsertDTO extends UserDTO{
    @NotBlank
    private String password;
}
