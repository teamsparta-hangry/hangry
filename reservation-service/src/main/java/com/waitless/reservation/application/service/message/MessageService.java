package com.waitless.reservation.application.service.message;

public interface MessageService {
    String buildVisitCompleteMessage(String slackId, String restaurantName, Long userId);
}
