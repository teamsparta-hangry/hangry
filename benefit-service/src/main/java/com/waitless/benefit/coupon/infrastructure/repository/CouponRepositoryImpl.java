package com.waitless.benefit.coupon.infrastructure.repository;

import static com.waitless.benefit.coupon.domain.entity.QCoupon.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.waitless.benefit.coupon.application.dto.CouponResponseDto;
import com.waitless.benefit.coupon.domain.entity.Coupon;
import com.waitless.benefit.coupon.domain.repository.CouponRepository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
public class CouponRepositoryImpl implements CouponRepository, CustomCouponRepository {

	private final JpaCouponRepository jpaCouponRepository;
	private final JPAQueryFactory queryFactory;

	public CouponRepositoryImpl(JpaCouponRepository jpaCouponRepository, EntityManager entityManager) {
		this.jpaCouponRepository = jpaCouponRepository;
		this.queryFactory = new JPAQueryFactory(entityManager);
	}

	@Override
	public Coupon save(Coupon coupon) {
		return jpaCouponRepository.save(coupon);
	}

	@Override
	public Optional<Coupon> findById(UUID id) {
		return jpaCouponRepository.findById(id);
	}

	@Override
	public Page<Coupon> findAndSearchCoupons(String title, Sort.Direction sortDirection, String sortBy, Pageable pageable) {
		OrderSpecifier<?> orderSpecifier = getOrderSpecifier(sortDirection, sortBy);
		BooleanBuilder builder = new BooleanBuilder();
		if (title != null && !title.isBlank()) {
			builder.and(coupon.title.containsIgnoreCase(title));
		}

		List<Coupon> users = queryFactory
			.selectFrom(coupon)
			.where(builder)
			.orderBy(orderSpecifier)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Long> countQuery = queryFactory
			.select(coupon.count())
			.from(coupon)
			.where(builder);

		return PageableExecutionUtils.getPage(users, pageable, countQuery::fetchOne);
	}

	private OrderSpecifier<?> getOrderSpecifier(Sort.Direction direction, String sortBy) {
		Order order = direction == Sort.Direction.ASC ? Order.ASC : Order.DESC;
		switch (sortBy) {
			case "title":
				return new OrderSpecifier<>(order, coupon.title);
			case "createdAt":
				return new OrderSpecifier<>(order, coupon.createdAt);
			case "updatedAt":
			default:
				return new OrderSpecifier<>(order, coupon.updatedAt);
		}
	}
}
