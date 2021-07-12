package com.mvam.etcontact.repositories;

import com.mvam.etcontact.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
