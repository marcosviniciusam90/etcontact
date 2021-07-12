package com.mvam.etcontact.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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

    @NotBlank(message = "Campo obrigatório")
    private String name;

    private String cpf;
    private LocalDate birthDate;

    @Email(message = "Email inválido")
    private String email;

    @Setter(AccessLevel.NONE)
    private Set<RoleDTO> roles = new HashSet<>();
}
