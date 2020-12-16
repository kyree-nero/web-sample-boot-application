package com.example.app.domain.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BadContentConstraintValidator implements 
  ConstraintValidator<BadContentConstraint, String> {

    @Override
    public void initialize(BadContentConstraint value) {
    }

    @Override
    public boolean isValid(String field,
      ConstraintValidatorContext cxt) {
        return field == null || (field != null && !field.equals("very specific bad content"));
    }

}