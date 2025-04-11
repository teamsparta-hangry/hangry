package com.waitless.restaurant.menu.application.mapper;

import com.waitless.restaurant.menu.application.dto.*;
import com.waitless.restaurant.menu.domain.entity.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuServiceMapper {
    @Mapping(source = "id", target = "menuId")
    CreatedMenuResponseDto toResponseDto(Menu menu);
    Menu toMenu(CreateMenuDto createMenuDto);

    @Mapping(source = "id", target = "menuId")
    MenuDto toMenuDto(Menu menu);

    Menu toMenuFromUpdateMenu(UpdateMenuDto updateMenuDto);

    @Mapping(source = "id", target = "menuId")
    UpdatedMenuResponseDto toUpdateResponseDto(Menu menu);
}
