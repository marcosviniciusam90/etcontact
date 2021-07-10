package com.mvam.etcontact.mappers;

import com.mvam.etcontact.dto.UserDTO;
import com.mvam.etcontact.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User dtoToEntity(UserDTO dto);

    UserDTO entityToDTO(User entity);
}
