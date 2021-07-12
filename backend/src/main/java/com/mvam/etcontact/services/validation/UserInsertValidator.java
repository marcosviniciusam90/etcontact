package com.mvam.etcontact.services.validation;

import com.mvam.etcontact.controllers.exceptions.FieldMessage;
import com.mvam.etcontact.dto.UserInsertDTO;
import com.mvam.etcontact.entities.User;
import com.mvam.etcontact.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDTO> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UserInsertValid ann) {
        // not necessary
    }

    @Override
    public boolean isValid(UserInsertDTO dto, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();

        Optional<User> user = userRepository.findByEmail(dto.getEmail());
        if(user.isPresent()) {
            list.add(new FieldMessage("email", "Email already exists"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
