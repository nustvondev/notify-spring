package vn.com.notification.infra.messaging.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import vn.com.notification.core.util.JsonUtil;
import vn.com.notification.infra.messaging.eventmodel.common.PayloadEvent;
import vn.com.notification.infra.repository.entity.NotificationTemplateEntity;

@Slf4j
@Component
@RequiredArgsConstructor
public class CampaignListener {
    @KafkaListener(
            topics = "${kafka.consumers.internal.topic}",
            groupId = "${kafka.consumers.internal.groupId}")
    public void consumer(String message) {
        try {
            log.info("Kafka consumer = {}", message);
//            var event = JsonUtil.jsonToObject(message, PayloadEvent.class);
//            var payload =
//                    JsonUtil.jsonToObject(
//                            JsonUtil.objectToJson(event.payload()), NotificationTemplateEntity.class);
//            log.info("Received campaign event: {}", payload);
        } catch (Exception e) {
            log.error("Failed to parse password status message", e);
            throw new IllegalStateException(e);
        }
    }


}
