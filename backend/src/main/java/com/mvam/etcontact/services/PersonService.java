package com.mvam.etcontact.services;

import com.mvam.etcontact.dto.PersonDTO;
import com.mvam.etcontact.entities.Person;
import com.mvam.etcontact.mappers.PersonMapper;
import com.mvam.etcontact.repositories.PersonRepository;
import com.mvam.etcontact.services.exceptions.DatabaseIntegrityException;
import com.mvam.etcontact.services.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {

    private final PersonRepository repository;
    private static final PersonMapper MAPPER = PersonMapper.INSTANCE;

    @Transactional(readOnly = true)
    public Page<PersonDTO> findAllByNameContaining(String name, Pageable pageable) {
        Page<Person> page = repository.findAllByNameContaining(name, pageable);
        return page.map(MAPPER::entityToDTO);
    }

    @Transactional(readOnly = true)
    public PersonDTO findById(Long id) {
        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        return MAPPER.entityToDTO(entity);
    }

    @Transactional
    public PersonDTO create(PersonDTO dto) {
        Person entity = new Person();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return MAPPER.entityToDTO(entity);
    }

    @Transactional
    public PersonDTO update(Long id, PersonDTO dto) {
        try {
            Person entity = repository.getById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return MAPPER.entityToDTO(entity);
        } catch (EntityNotFoundException ex) {
            throw new ResourceNotFoundException(id);
        }
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException ex) {
            throw new DatabaseIntegrityException(id);
        }
    }

    private void copyDtoToEntity(PersonDTO dto, Person entity) {
        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setBirthDate(dto.getBirthDate());
    }
}
