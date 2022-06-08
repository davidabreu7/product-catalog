package com.devlab.prodcatalog.backend.service.validation;

import com.devlab.prodcatalog.backend.controller.exceptions.FieldError;
import com.devlab.prodcatalog.backend.dto.UserInsertDto;
import com.devlab.prodcatalog.backend.entities.User;
import com.devlab.prodcatalog.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDto> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UserInsertValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserInsertDto dto, ConstraintValidatorContext context) {

        List<FieldError> list = new ArrayList<>();

        User user = userRepository.findByEmail(dto.getEmail());

        if (user != null){
            list.add(new FieldError("email", "Email ja existe"));
        }

        for (FieldError e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getError()).addPropertyNode(e.getField())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
