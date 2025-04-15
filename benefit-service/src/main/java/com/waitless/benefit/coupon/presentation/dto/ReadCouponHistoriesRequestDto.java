package com.waitless.benefit.coupon.presentation.dto;

import org.springframework.data.domain.Sort;

public record ReadCouponHistoriesRequestDto(
	String title, int page, int size, Sort.Direction sortDirection, String sortBy, Long userId) {
}
