package com.github.jefersonalmeida.ifood.register.dto;

import com.github.jefersonalmeida.ifood.register.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi")
public interface RestaurantMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "location.id", ignore = true)
    Restaurant toEntity(CreateRestaurantDTO d);

    void toEntity(UpdateRestaurantDTO d, @MappingTarget Restaurant e);

    @Mapping(target = "createdAt", dateFormat = "dd/MM/yyyy HH:mm:ss")
    @Mapping(target = "updatedAt", dateFormat = "dd/MM/yyyy HH:mm:ss")
    RestaurantDTO toDTO(Restaurant e);
}
