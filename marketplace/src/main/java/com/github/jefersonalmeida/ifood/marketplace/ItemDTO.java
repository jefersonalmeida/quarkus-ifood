package com.github.jefersonalmeida.ifood.marketplace;

import io.vertx.mutiny.sqlclient.Row;

import java.math.BigDecimal;
import java.util.UUID;

public class ItemDTO {
    public UUID id;
    public String name;
    public String description;
    public BigDecimal price;

    public static ItemDTO from(Row row) {
        ItemDTO dto = new ItemDTO();
        dto.id = row.getUUID("id");
        dto.name = row.getString("name");
        dto.description = row.getString("description");
        dto.price = row.getBigDecimal("price");
        return dto;
    }
}
