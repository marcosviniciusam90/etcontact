package com.mvam.etcontact.repositories;

import com.mvam.etcontact.entities.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Page<Person> findAllByNameContaining(String name, Pageable pageable);
}
