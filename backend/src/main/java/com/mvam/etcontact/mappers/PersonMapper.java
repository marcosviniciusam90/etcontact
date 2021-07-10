package com.mvam.etcontact.mappers;

import com.mvam.etcontact.dto.PersonDTO;
import com.mvam.etcontact.entities.Person;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    Person dtoToEntity(PersonDTO dto);

    PersonDTO entityToDTO(Person entity);
}
