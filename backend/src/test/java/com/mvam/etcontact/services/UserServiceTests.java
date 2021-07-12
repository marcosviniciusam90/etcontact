package com.mvam.etcontact.services;

import com.mvam.etcontact.dto.UserDTO;
import com.mvam.etcontact.dto.UserInsertDTO;
import com.mvam.etcontact.dto.UserUpdateDTO;
import com.mvam.etcontact.entities.Role;
import com.mvam.etcontact.entities.User;
import com.mvam.etcontact.repositories.RoleRepository;
import com.mvam.etcontact.repositories.UserRepository;
import com.mvam.etcontact.services.exceptions.ResourceNotFoundException;
import com.mvam.etcontact.utils.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserServiceTests {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    private long existingId;
    private long nonExistingId;
    private User user;
    private UserInsertDTO userInsertDTO;
    private UserUpdateDTO userUpdateDTO;
    private PageImpl<User> page;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 2L;
        user = Factory.createUser(existingId);
        userInsertDTO = Factory.createUserInsertDTO(existingId);
        userUpdateDTO = Factory.createUserUpdateDTO(existingId);
        Role role = Factory.createRole(existingId);
        page = new PageImpl<>(Collections.singletonList((user)));

        doNothing().when(repository).deleteById(existingId);
        doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);

        when(repository.findAllByNameContaining(any(String.class), any(Pageable.class))).thenReturn(page);

        when(repository.findById(existingId)).thenReturn(Optional.of(user));
        when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        when(repository.save(any(User.class))).thenReturn(user);

        when(repository.getById(existingId)).thenReturn(user);
        when(repository.getById(nonExistingId)).thenThrow(EntityNotFoundException.class);

        when(roleRepository.getById(existingId)).thenReturn(role);
        when(roleRepository.getById(nonExistingId)).thenThrow(EntityNotFoundException.class);
    }

    @Test
    void findAllByNameContainingShouldReturnPage() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<UserDTO> result = service.findAllByNameContaining(user.getName(), pageRequest);

        Assertions.assertNotNull(result);
        verify(repository, times(1)).findAllByNameContaining(user.getName(), pageRequest);
    }

    @Test
    void findByIdShouldReturnUserDTOWhenIdExists() {
        UserDTO result = service.findById(existingId);
        Assertions.assertNotNull(result);
        verify(repository, times(1)).findById(existingId);
    }

    @Test
    void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(nonExistingId);
        });
        verify(repository, times(1)).findById(nonExistingId);
    }

    @Test
    void createShouldReturnUserDTO() {
        UserDTO result = service.create(userInsertDTO);
        Assertions.assertNotNull(result);
        verify(repository, times(1)).save(any(User.class));
    }

    @Test
    void updateShouldReturnUserDTOWhenIdExists() {
        UserDTO result = service.update(existingId, userUpdateDTO);
        Assertions.assertNotNull(result);
        verify(repository, times(1)).getById(existingId);
        verify(repository, times(1)).save(any(User.class));
    }

    @Test
    void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.update(nonExistingId, userUpdateDTO);
        });
        verify(repository, times(1)).getById(nonExistingId);
    }

    @Test
    void deleteShouldDoNothingWhenIdExists() {
        Assertions.assertDoesNotThrow(() -> {
            service.delete(existingId);
        });
        verify(repository, times(1)).deleteById(existingId);
    }

    @Test
    void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(nonExistingId);
        });
        verify(repository, times(1)).deleteById(nonExistingId);
    }

}
