package com.mvam.etcontact.controllers;

import com.mvam.etcontact.dto.PersonDTO;
import com.mvam.etcontact.event.CreatedResourceEvent;
import com.mvam.etcontact.repositories.PersonRepository;
import com.mvam.etcontact.services.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/persons")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonController {

    private final PersonRepository repository;
    private final PersonService service;
    private final ApplicationEventPublisher publisher;

    //@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
    @GetMapping
    public Page<PersonDTO> findAllByNameContaining(
            @RequestParam(required = false, defaultValue = "") String name, Pageable pageable) {
        return service.findAllByNameContaining(name, pageable);
    }

    //@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
    @GetMapping("/{id}")
    public PersonDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    //@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonDTO create(@RequestBody PersonDTO dto, HttpServletResponse response) {
        dto = service.create(dto);
        publisher.publishEvent(new CreatedResourceEvent(this, dto.getId(), response));
        return dto;
    }

    //@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PersonDTO update(@PathVariable Long id, @RequestBody PersonDTO dto) {
        return service.update(id, dto);
    }

    //@PreAuthorize("hasAuthority('ROLE_REMOVER_PESSOA') and #oauth2.hasScope('write')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir (@PathVariable Long id) {
        service.delete(id);
    }
}
