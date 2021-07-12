package com.mvam.etcontact.services;

import com.mvam.etcontact.dto.ContactDTO;
import com.mvam.etcontact.entities.Contact;
import com.mvam.etcontact.mappers.ContactMapper;
import com.mvam.etcontact.repositories.ContactRepository;
import com.mvam.etcontact.repositories.UserRepository;
import com.mvam.etcontact.services.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ContactService {

    private static final ContactMapper MAPPER = ContactMapper.INSTANCE;
    private final ContactRepository repository;
    private final UserRepository userRepository;

    @Transactional
    public ContactDTO add(Long userId, ContactDTO dto) {
        Contact entity = new Contact();
        copyDtoToEntity(userId, dto, entity);

        entity = repository.save(entity);
        return MAPPER.entityToDTO(entity);
    }

    private void copyDtoToEntity(Long userId, ContactDTO dto, Contact entity) {
        entity.setName(dto.getName());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setUser(
                userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(userId)));
    }
}
