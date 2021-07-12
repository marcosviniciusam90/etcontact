package com.mvam.etcontact.controllers;

import com.mvam.etcontact.dto.UserDTO;
import com.mvam.etcontact.dto.UserInsertDTO;
import com.mvam.etcontact.dto.UserUpdateDTO;
import com.mvam.etcontact.event.CreatedResourceEvent;
import com.mvam.etcontact.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    
    private final UserService service;
    private final ApplicationEventPublisher publisher;

    //@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
    @GetMapping
    public Page<UserDTO> findAllByNameContaining(
            @RequestParam(required = false, defaultValue = "") String name, Pageable pageable) {
        return service.findAllByNameContaining(name, pageable);
    }

    //@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
    @GetMapping("/{id}")
    public UserDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    //@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@Valid @RequestBody UserInsertDTO insertDto, HttpServletResponse response) {
        UserDTO dto = service.create(insertDto);
        publisher.publishEvent(new CreatedResourceEvent(this, dto.getId(), response));
        return dto;
    }

    //@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO update(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO updateDto) {
        return service.update(id, updateDto);
    }

    //@PreAuthorize("hasAuthority('ROLE_REMOVER_PESSOA') and #oauth2.hasScope('write')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir (@PathVariable Long id) {
        service.delete(id);
    }
}
