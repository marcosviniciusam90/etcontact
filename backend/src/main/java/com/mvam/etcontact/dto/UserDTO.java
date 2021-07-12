package com.mvam.etcontact.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserDTO implements Serializable {

    @EqualsAndHashCode.Include
    private Long id;

    private String name;
    private String cpf;
    private LocalDate birthDate;
    private String email;
}
