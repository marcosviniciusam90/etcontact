package com.mvam.etcontact.controllers;

import com.mvam.etcontact.dto.ContactDTO;
import com.mvam.etcontact.dto.UserDTO;
import com.mvam.etcontact.dto.UserInsertDTO;
import com.mvam.etcontact.dto.UserUpdateDTO;
import com.mvam.etcontact.event.CreatedResourceEvent;
import com.mvam.etcontact.services.ContactService;
import com.mvam.etcontact.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController implements SwaggerSecuredRestController {
    
    private final UserService service;
    private final ContactService contactService;
    private final ApplicationEventPublisher publisher;

    @PreAuthorize("hasAnyRole('ADMIN', 'VISITOR') and #oauth2.hasScope('read')")
    @GetMapping
    public Page<UserDTO> findAllByNameContaining(
            @RequestParam(required = false, defaultValue = "") String name, Pageable pageable) {
        return service.findAllByNameContaining(name, pageable);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'VISITOR') and #oauth2.hasScope('read')")
    @GetMapping("/{id}")
    public UserDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN') and #oauth2.hasScope('write')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@Valid @RequestBody UserInsertDTO insertDto, HttpServletResponse response) {
        UserDTO dto = service.create(insertDto);
        publisher.publishEvent(new CreatedResourceEvent(this, dto.getId(), response));
        return dto;
    }

    @PreAuthorize("hasAnyRole('ADMIN') and #oauth2.hasScope('write')")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO update(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO updateDto) {
        return service.update(id, updateDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN') and #oauth2.hasScope('write')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'VISITOR') and #oauth2.hasScope('write')")
    @ResponseStatus(HttpStatus.OK)
    public ContactDTO addContact(@PathVariable Long id, @Valid @RequestBody ContactDTO contactDTO, HttpServletResponse response) {
        return contactService.add(id, contactDTO);
    }
}
