package com.github.jefersonalmeida.ifood.register.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class MenuDTO {
    public UUID id;
    public String name;
    public String description;
    public RestaurantDTO restaurant;
    public BigDecimal price;
}
