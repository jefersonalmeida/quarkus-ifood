package com.github.jefersonalmeida.ifood.register.dto;

import com.github.jefersonalmeida.ifood.register.Restaurant;
import com.github.jefersonalmeida.ifood.register.infra.DTO;
import com.github.jefersonalmeida.ifood.register.infra.ValidDTO;

import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@ValidDTO
public class CreateRestaurantDTO implements DTO {
    @Size(min = 3, max = 100)
    public String name;

    @Pattern(regexp = "[0-9]{2}\\.[0-9]{3}\\.[0-9]{3}/[0-9]{4}-[0-9]{2}")
    @NotNull
    public String document;

    public LocationDTO location;

    @Override
    public boolean isValid(ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        if (Restaurant.find("document", document).count() > 0) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Documento já cadastrado")
                    .addPropertyNode("document")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
