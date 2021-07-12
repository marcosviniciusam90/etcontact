package com.mvam.etcontact.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO implements Serializable {
    private Long id;

    private String authority;
}
