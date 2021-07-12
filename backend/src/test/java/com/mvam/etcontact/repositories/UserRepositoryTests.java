package com.mvam.etcontact.repositories;

import com.mvam.etcontact.entities.User;
import com.mvam.etcontact.utils.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

@DataJpaTest
@TestPropertySource(locations="classpath:application-test.properties")
class UserRepositoryTests {

    @Autowired
    private UserRepository repository;

    private long existingId;
    private long nonExistingId;
    private long counTotal;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 1000L;
        counTotal = 2L;
    }

    @Test
    void saveShouldPersistWithAutoIncrementWhenIdIsNull() {
        User entity = Factory.createUser(null);

        entity = repository.save(entity);

        Assertions.assertNotNull(entity.getId());
        Assertions.assertEquals(counTotal +1, entity.getId());
    }

    @Test
    void saveShouldPersistWithoutAutoIncrementWhenIdIsSet() {
        User entity = Factory.createUser(existingId);

        entity = repository.save(entity);

        Assertions.assertEquals(existingId, entity.getId());
    }

    @Test
    void findByIdShouldReturnAPresentOptionalObjectWhenIdExists() {
        Optional<User> result = repository.findById(existingId);
        Assertions.assertTrue(result.isPresent());
    }

    @Test
    void findByIdShouldReturnAEmptyOptionalObjectWhenIdDoesNotExist() {
        Optional<User> result = repository.findById(nonExistingId);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    void deleteShouldDeleteObjectWhenIdExists() {
        repository.deleteById(existingId);
        Optional<User> result = repository.findById(existingId);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            repository.deleteById(nonExistingId);
        });
    }
}
