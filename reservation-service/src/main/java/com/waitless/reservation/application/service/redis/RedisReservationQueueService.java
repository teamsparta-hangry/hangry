package com.waitless.reservation.application.service.redis;

import com.waitless.common.exception.BusinessException;
import com.waitless.reservation.exception.exception.ReservationErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RedisReservationQueueService {

    private final StringRedisTemplate redisTemplate;

    private static final String QUEUE_PREFIX = "reservation:queue:";

    public void registerToWaitingQueue(UUID reservationId, LocalDate reservationDate, UUID restaurantId, Long reservationNumber) {
        String zsetKey = QUEUE_PREFIX + restaurantId + ":" + reservationDate;
        redisTemplate.opsForZSet().add(zsetKey, String.valueOf(reservationId), reservationNumber);
    }

    public void removeFromWaitingQueue(UUID reservationId, LocalDate reservationDate, UUID restaurantId) {
        String zsetKey = QUEUE_PREFIX + restaurantId + ":" + reservationDate;
        redisTemplate.opsForZSet().remove(zsetKey, String.valueOf(reservationId));
    }

    public Long findCurrentNumberFromWaitingQueue(UUID reservationId, UUID restaurantId) {
        String key = QUEUE_PREFIX + restaurantId + ":" + LocalDate.now();
        Long rank = redisTemplate.opsForZSet().rank(key, reservationId.toString());

        if(rank == null){
            throw BusinessException.from(ReservationErrorCode.RESERVATION_NOT_FOUND);
        }

        return rank + 1;
    }
}