package com.github.jefersonalmeida.ifood.register.dto;

import com.github.jefersonalmeida.ifood.register.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi")
public interface MenuMapper {

    MenuDTO toDTO(Menu e);

    Menu toEntity(CreateMenuDTO d);

    void toEntity(UpdateMenuDTO d, @MappingTarget Menu e);
}
