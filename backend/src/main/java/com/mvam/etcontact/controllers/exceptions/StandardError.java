package com.mvam.etcontact.controllers.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.Instant;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class StandardError implements Serializable {
    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}
