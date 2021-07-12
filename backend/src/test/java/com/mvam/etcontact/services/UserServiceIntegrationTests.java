package com.mvam.etcontact.services;

import com.mvam.etcontact.dto.UserDTO;
import com.mvam.etcontact.repositories.UserRepository;
import com.mvam.etcontact.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class UserServiceIntegrationTests {

    @Autowired
    private UserService service;

    @Autowired
    private UserRepository repository;

    private Long existingId;
    private Long nonExistingId;
    private Long countTotal;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 1000L;
        countTotal = 2L;
    }

    @Test
    void deleteShouldDeleteResourceWhenIdExists() {
        service.delete(existingId);
        Assertions.assertEquals(countTotal - 1, repository.count());
    }

    @Test
    void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(nonExistingId);
        });
    }

    @Test
    void findAllByNameContainingShouldReturnPageWhenPage0Size10() {
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<UserDTO> result = service.findAllByNameContaining("", pageRequest);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(page, result.getNumber());
        Assertions.assertEquals(size, result.getSize());
        Assertions.assertEquals(countTotal, result.getTotalElements());
    }

    @Test
    void findAllByNameContainingShouldReturnEmptyPageWhenPageDoesNotExist() {
        int page = 50;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<UserDTO> result = service.findAllByNameContaining("", pageRequest);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void findAllByNameContainingShouldReturnSortedPageWhenSortByName() {
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("name"));
        Page<UserDTO> result = service.findAllByNameContaining("", pageRequest);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals("Administrador", result.getContent().get(0).getName());
        Assertions.assertEquals("Marcos Vinicius", result.getContent().get(1).getName());
    }

    @Test
    void findAllByNameContainingShouldReturnSpecificUserWhenGivenName() {
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<UserDTO> result = service.findAllByNameContaining("Marcos", pageRequest);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals("Marcos Vinicius", result.getContent().get(0).getName());
    }


}
