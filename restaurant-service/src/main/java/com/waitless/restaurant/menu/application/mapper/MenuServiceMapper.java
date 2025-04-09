package com.waitless.restaurant.menu.application.mapper;

import com.waitless.restaurant.menu.application.dto.CreateMenuDto;
import com.waitless.restaurant.menu.application.dto.CreatedMenuResponseDto;
import com.waitless.restaurant.menu.application.dto.MenuDto;
import com.waitless.restaurant.menu.domain.entity.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Optional;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuServiceMapper {
    @Mapping(source = "id", target = "menuId")
    CreatedMenuResponseDto toResponseDto(Menu menu);
    Menu toMenu(CreateMenuDto createMenuDto);

    @Mapping(source = "id", target = "menuId")
    MenuDto toGetResponseDto(Menu menu);
}
