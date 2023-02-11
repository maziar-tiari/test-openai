package com.buddies.services.userprofile.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.buddies.services.userprofile.types.Language;

import jakarta.validation.ConstraintValidatorContext;

public class LanguageTypeValidatorTest {
  private LanguageTypeValidator validator = new LanguageTypeValidator();
  
  @Mock
  private ConstraintValidatorContext context;

  @Test
  void testInvalidLanguages_shouldReturnFalse() {
    var inValidLanguages = List.of("inv1", "inv2");
    assertFalse(validator.isValid(inValidLanguages, context));
  }

  @Test
  void testValidLanguages_shouldReturnTrue() {
    var validLanguages = Language.all().stream().limit(3).toList();
    assertTrue(validator.isValid(validLanguages, context));
  }

  @Test
  void testValidNInvalidLanguages_shouldReturn() {
    var validLanguages = Language.all().stream().limit(2);
    var invalidLanguages = Stream.of("invalid_lang_1", "invalid_lang_1");
    var languages = Stream.concat(validLanguages, invalidLanguages).toList();
    assertFalse(validator.isValid(languages, context));
  }
}
