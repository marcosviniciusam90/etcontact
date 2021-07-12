package com.mvam.etcontact.controllers;

import com.mvam.etcontact.config.swagger.SwaggerConfiguration;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = SwaggerConfiguration.AUTH_SCHEME)
public interface SwaggerSecuredRestController {
}
