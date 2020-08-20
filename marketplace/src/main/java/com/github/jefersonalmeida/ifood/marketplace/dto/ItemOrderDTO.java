package com.github.jefersonalmeida.ifood.marketplace.dto;

import java.math.BigDecimal;

public class ItemOrderDTO {

    public String name;

    public String description;

    public BigDecimal price;

    public ItemOrderDTO() {
        super();
    }

    public ItemOrderDTO(String name, String description, BigDecimal price) {
        super();
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
