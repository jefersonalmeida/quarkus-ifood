package com.github.jefersonalmeida.ifood.register.dto;

import com.github.jefersonalmeida.ifood.register.Item;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi")
public interface ItemMapper {

    ItemDTO toDTO(Item e);

    Item toEntity(CreateItemDTO d);

    void toEntity(UpdateItemDTO d, @MappingTarget Item e);
}
