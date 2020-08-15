package com.github.jefersonalmeida.ifood.register.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public class RestaurantDTO {
    public UUID id;
    public String name;
    public String owner;
    public String document;
    public LocationDTO location;
    public OffsetDateTime createdAt;
    public OffsetDateTime updatedAt;
}
