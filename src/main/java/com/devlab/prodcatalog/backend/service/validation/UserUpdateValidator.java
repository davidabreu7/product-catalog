package com.devlab.prodcatalog.backend.service.validation;

import com.devlab.prodcatalog.backend.controller.exceptions.FieldError;
import com.devlab.prodcatalog.backend.dto.UserUpdateDto;
import com.devlab.prodcatalog.backend.entities.User;
import com.devlab.prodcatalog.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserUpdateValidator implements ConstraintValidator<UserUpdateValid, UserUpdateDto> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    HttpServletRequest request;

    @Override
    public void initialize(UserUpdateValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserUpdateDto dto, ConstraintValidatorContext context) {

        @SuppressWarnings("unchecked")
        var pathVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Long userId = Long.valueOf(pathVars.get("id"));

        List<FieldError> list = new ArrayList<>();

        User user = userRepository.findByEmail(dto.getEmail());

        if (user != null && !userId.equals(user.getId())) {
            list.add(new FieldError("email", "Email ja existe"));
        }

        for (FieldError e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getError()).addPropertyNode(e.getField()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}
