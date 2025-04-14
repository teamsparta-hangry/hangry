package com.waitless.restaurant.menu.infrastructure.db;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.waitless.restaurant.menu.domain.dto.MenuSearchDomainDto;
import com.waitless.restaurant.menu.domain.entity.Menu;
import com.waitless.restaurant.menu.domain.entity.QMenu;
import com.waitless.restaurant.menu.domain.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class MenuRepositoryImpl implements MenuRepository {

    private final MenuJpaRepository menuJpaRepository;
    private final JPAQueryFactory queryFactory;

    public Menu save(Menu menu) {
        return menuJpaRepository.save(menu);
    }

    public Optional<Menu> getMenu(UUID id) {return menuJpaRepository.findById(id);}

    public List<Menu> findAllByRestaurantId(UUID restaurantId) {
        return menuJpaRepository.findAllByRestaurantId(restaurantId);
    }

    public Page<Menu> searchMenu(MenuSearchDomainDto menuSearchDomainDto, Pageable pageable) {
        QMenu menu = QMenu.menu;
        BooleanBuilder builder = new BooleanBuilder();

        Optional.ofNullable(menuSearchDomainDto.minPrice())
                .ifPresent(min -> builder.and(menu.price.goe(min)));

        Optional.ofNullable(menuSearchDomainDto.maxPrice())
                .ifPresent(max -> builder.and(menu.price.loe(max)));

        Optional.ofNullable(menuSearchDomainDto.category())
                .ifPresent(category -> builder.and(menu.menuCategory.eq(category)));

        OrderSpecifier<?> sort;
        if (Optional.ofNullable(menuSearchDomainDto.sortBy()).orElse("createdAt").equals("price")) {
            sort = menu.price.desc();
        } else {
            sort = menu.createdAt.desc();
        }

        List<Menu> content = queryFactory
                .selectFrom(menu)
                .where(builder)
                .orderBy(sort)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(menu.count())
                .from(menu)
                .where(builder)
                .fetchOne();
        if (total == null) total = 0L;

        return new PageImpl<>(content, pageable, total);
    }
}
