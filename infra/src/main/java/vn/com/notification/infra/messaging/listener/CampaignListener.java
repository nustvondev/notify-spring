package vn.com.notification.infra.messaging.listener;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import vn.com.notification.core.dto.params.NotificationParams;
import vn.com.notification.core.usecase.CampaignsUseCase;
import vn.com.notification.core.util.JsonUtil;
import vn.com.notification.infra.messaging.eventmodel.common.PayloadEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class CampaignListener {
    private final CampaignsUseCase campaignsUseCase;

    @KafkaListener(
            topics = "${kafka.consumers.internal.topic}",
            groupId = "${kafka.consumers.internal.groupId}")
    public void consumer(String message) {
        try {
            log.info("Kafka consumer = {}", message);
            var event = JsonUtil.jsonToObject(message, PayloadEvent.class);
            var payload =
                    JsonUtil.jsonToObject(JsonUtil.objectToJson(event.payload()), NotificationParams.class);
            log.info("Received campaign event: {}", payload);
            var notification = convertDTO(payload);
            campaignsUseCase.createNotifications(notification);
        } catch (Exception e) {
            log.error("Failed to parse password status message", e);
            throw new IllegalStateException(e);
        }
    }

    @Nonnull
    private NotificationParams convertDTO(NotificationParams payload) {
        var notification = new NotificationParams();
        notification.setUserId(payload.getUserId());
        notification.setCode(payload.getCode());
        notification.setTitle(payload.getTitle());
        notification.setContent(payload.getContent());
        notification.setContentHtml(payload.getContentHtml());
        notification.setSmsContent(payload.getSmsContent());
        notification.setRead(payload.isRead());
        notification.setMeta("{}");
        return notification;
    }
}
