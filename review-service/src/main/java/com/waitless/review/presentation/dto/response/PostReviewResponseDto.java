package com.waitless.review.presentation.dto.response;

import com.waitless.review.application.dto.result.PostReviewResult;
import com.waitless.review.domain.vo.Rating;
import com.waitless.review.domain.vo.ReviewType;

import java.time.LocalDateTime;
import java.util.UUID;

public record PostReviewResponseDto(
    UUID reviewId,
    Long userId,
    UUID restaurantId,
    String content,
    Rating rating,
    ReviewType reviewType,
    LocalDateTime createdAt
    ) {
    public static PostReviewResponseDto from(PostReviewResult result) {
        return new PostReviewResponseDto(
                result.reviewId(),
                result.userId(),
                result.restaurantId(),
                result.content(),
                result.rating(),
                result.reviewType(),
                result.createdAt()
        );
    }
}
