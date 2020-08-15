package com.github.jefersonalmeida.ifood.register.infra;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValidDTOValidator.class})
@Documented
public @interface ValidDTO {

    String message() default "{com.github.jefersonalmeida.ifood.register.infra.ValidDTO.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
