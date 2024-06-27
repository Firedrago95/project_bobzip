package project.bobzip.entity.recipe.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class ValidListValidator implements ConstraintValidator<NotEmptyList, List<?>> {
    @Override
    public boolean isValid(List<?> value, ConstraintValidatorContext context) {
        return !(value != null || value.contains(""));
    }
}
