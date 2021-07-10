package com.mvam.etcontact.dto;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserDTO implements Serializable {

    @EqualsAndHashCode.Include
    private Long id;

    private String name;
    private String email;
}
