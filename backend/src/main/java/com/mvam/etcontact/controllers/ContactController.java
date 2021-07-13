package com.mvam.etcontact.controllers;

import com.mvam.etcontact.dto.ContactDTO;
import com.mvam.etcontact.services.ContactService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/contacts")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ContactController implements SwaggerSecuredRestController {
    
    private final ContactService service;

    @PreAuthorize("hasAnyRole('ADMIN', 'VISITOR') and #oauth2.hasScope('write')")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContactDTO update(@PathVariable Long id, @Valid @RequestBody ContactDTO dto) {
        return service.update(id, dto);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'VISITOR') and #oauth2.hasScope('write')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
