package com.mvam.etcontact.services;

import com.mvam.etcontact.dto.*;
import com.mvam.etcontact.entities.Contact;
import com.mvam.etcontact.entities.Role;
import com.mvam.etcontact.entities.User;
import com.mvam.etcontact.mappers.ContactMapper;
import com.mvam.etcontact.mappers.UserMapper;
import com.mvam.etcontact.repositories.RoleRepository;
import com.mvam.etcontact.repositories.UserRepository;
import com.mvam.etcontact.services.exceptions.DatabaseIntegrityException;
import com.mvam.etcontact.services.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserService implements UserDetailsService {

    private static final UserMapper MAPPER = UserMapper.INSTANCE;
    private static final ContactMapper CONTACT_MAPPER = ContactMapper.INSTANCE;
    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found"));
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> findAllByNameContaining(String name, PageRequest pageRequest) {
        Page<User> page = repository.findAllByNameContaining(name, pageRequest);
        return page.map(MAPPER::entityToDTO);
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        User entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        return MAPPER.entityToDTO(entity);
    }

    @Transactional
    public UserDTO create(UserInsertDTO dto) {
        try {
            User entity = new User();
            copyDtoToEntity(dto, entity);
            entity.setPassword(passwordEncoder.encode(dto.getPassword()));

            entity = repository.save(entity);
            return MAPPER.entityToDTO(entity);
        } catch (EntityNotFoundException ex) {
            throw new ResourceNotFoundException();
        }
    }

    @Transactional
    public UserDTO update(Long id, UserUpdateDTO dto) {
        try {
            User entity = repository.getById(id);
            copyDtoToEntity(dto, entity);
            entity.setPassword(passwordEncoder.encode(dto.getPassword()));

            entity = repository.save(entity);
            return MAPPER.entityToDTO(entity);
        } catch (EntityNotFoundException ex) {
            throw new ResourceNotFoundException();
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

    private void copyDtoToEntity(UserDTO dto, User entity) {

        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setBirthDate(dto.getBirthDate());
        entity.setEmail(dto.getEmail());

        entity.getRoles().clear();
        for (RoleDTO roleDto : dto.getRoles()) {
            Role role = roleRepository.getById(roleDto.getId());
            entity.getRoles().add(role);
        }

        entity.getContacts().clear();
        for (ContactDTO contactDTO : dto.getContacts()) {
            Contact contact = CONTACT_MAPPER.dtoToEntity(contactDTO);
            contact.setUser(entity);
            entity.getContacts().add(contact);
        }
    }
}
