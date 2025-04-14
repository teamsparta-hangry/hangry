package com.waitless.restaurant.menu.application.service;

import com.waitless.restaurant.menu.application.dto.CreateMenuDto;
import com.waitless.restaurant.menu.application.dto.CreatedMenuResponseDto;
import com.waitless.restaurant.menu.application.dto.MenuDto;
import com.waitless.restaurant.menu.application.dto.UpdateMenuDto;
import com.waitless.restaurant.menu.application.dto.UpdatedMenuResponseDto;
import com.waitless.restaurant.menu.application.dto.*;
import com.waitless.restaurant.menu.domain.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface MenuService {
    CreatedMenuResponseDto createMenu(CreateMenuDto createMenuDto);

    MenuDto getMenu(UUID id);

    MenuDto deleteMenu(UUID id);

    UpdatedMenuResponseDto updateMenu(UUID id, UpdateMenuDto updateMenuDto);

    Page<SearchResponseDto> searchMenu(SearchMenuDto searchMenuDto, Pageable pageable);

    List<MenuDto> getMenus(UUID id);

    void deleteAllMenusByRestaurantId(UUID RestaurantId);
}
