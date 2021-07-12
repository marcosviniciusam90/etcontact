package com.mvam.etcontact.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    @Pattern(regexp ="[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}\\-[0-9]{2}", message = "Invalid CPF")
    private String cpf;

    @NotNull
    @PastOrPresent
    private LocalDate birthDate;

    @NotBlank
    @Email
    private String email;

    @Setter(AccessLevel.NONE)
    private Set<RoleDTO> roles = new HashSet<>();
}
