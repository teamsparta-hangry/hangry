package com.waitless.review.application.service;

import com.waitless.common.event.ReviewCreatedEvent;
import com.waitless.review.application.dto.command.PostReviewCommand;
import com.waitless.review.application.dto.result.PostReviewResult;
import com.waitless.review.application.mapper.ReviewServiceMapper;
import com.waitless.review.application.port.out.ReviewOutboxPort;
import com.waitless.review.domain.entity.Review;
import com.waitless.review.domain.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewServiceMapper reviewServiceMapper;
    private final ReviewOutboxPort reviewOutboxPort;

    @Override
    @Transactional
    public PostReviewResult createReview(PostReviewCommand command) {
        Review review = reviewServiceMapper.toEntity(command);
        Review saved = reviewRepository.save(review);

        ReviewCreatedEvent event = ReviewCreatedEvent.builder()
                .reviewId(saved.getId())
                .userId(saved.getUserId())
                .restaurantId(saved.getRestaurantId())
                .build();
        reviewOutboxPort.saveReviewCreatedEvent(event);
        return PostReviewResult.from(saved);
    }
}
