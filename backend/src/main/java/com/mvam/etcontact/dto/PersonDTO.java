package com.mvam.etcontact.dto;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PersonDTO implements Serializable {

    @EqualsAndHashCode.Include
    private Long id;

    private String name;
    private String cpf;
    private LocalDate birthDate;
}
