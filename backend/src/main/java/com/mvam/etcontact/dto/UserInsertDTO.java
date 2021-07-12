package com.mvam.etcontact.dto;

import com.mvam.etcontact.services.validation.UserInsertValid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@UserInsertValid
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor()
public class UserInsertDTO extends UserDTO{
    private String password;
}
