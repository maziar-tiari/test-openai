package com.buddies.services.userprofile.validation;

import java.util.List;

import com.buddies.services.userprofile.types.Language;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LanguageTypeValidator implements ConstraintValidator<ValidLanguage, List<String>> {

  @Override
  public boolean isValid(List<String> value, ConstraintValidatorContext context) {
    return value.stream().allMatch(l -> Language.all().contains(l));
  }
  
}
