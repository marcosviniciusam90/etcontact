package com.mvam.etcontact.mappers;

import com.mvam.etcontact.dto.ContactDTO;
import com.mvam.etcontact.entities.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ContactMapper {

    ContactMapper INSTANCE = Mappers.getMapper(ContactMapper.class);

    Contact dtoToEntity(ContactDTO dto);

    @Mapping(source = "user.id", target = "userId")
    ContactDTO entityToDTO(Contact entity);
}
