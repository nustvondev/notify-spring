package vn.com.notification.infra.messaging.impl;

import static vn.com.notification.infra.configuration.kafka.KafkaEventProducerConfig.OTT_LOGIN_KAFKA_TEMPLATE;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import vn.com.notification.infra.messaging.EventType;
import vn.com.notification.infra.messaging.KafkaEventProducer;
import vn.com.notification.infra.messaging.eventmodel.login.BaseEvent;
import vn.com.notification.infra.messaging.eventmodel.ott.OTTLoginEventPayload;

@Service
@RequiredArgsConstructor
public class OTTLoginEventProducer implements KafkaEventProducer {
    @Qualifier(OTT_LOGIN_KAFKA_TEMPLATE)
    private final KafkaTemplate<String, OTTLoginEventPayload> kafkaTemplate;

    @Override
    public List<EventType> supportEventType() {
        return List.of(EventType.OTT_LOGIN);
    }

    @Override
    public <T extends BaseEvent> void publishEvent(T event, EventType eventType) {
        kafkaTemplate.send(mapToNotificationObject((OTTLoginEventPayload) event));
    }
}
