package project.bobzip.entity.recipe.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class ValidListValidator implements ConstraintValidator<NotEmptyList, List<?>> {
    @Override
    public boolean isValid(List<?> value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        for (Object element : value) {
            if (element instanceof String && ((String) element).isEmpty()) {
                return false;
            } else if (element instanceof Integer && ((Integer) element) == 0) {
                return false;
            }
        }

        return true;
    }
}
