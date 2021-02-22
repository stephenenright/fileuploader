package com.github.stephenenright.fileuploader.common.validation.annotation;

import com.github.stephenenright.fileuploader.common.utils.StringUtils;
import com.github.stephenenright.fileuploader.common.validation.utils.ValidationUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ISODateTime.IS08601DateTimeValidator.class)
public @interface ISODateTime {
    String message() default "must be a valid date time";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class IS08601DateTimeValidator implements ConstraintValidator<ISODateTime, String> {
        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (StringUtils.isNullOrEmpty(value)) {
                return true;
            }

            return ValidationUtils.isISODateTime(value);
        }
    }
}

