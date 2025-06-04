package vn.com.notification.infra.messaging.impl;

import static vn.com.notification.infra.configuration.kafka.KafkaEventProducerConfig.CAMPING_KAFKA_TEMPLATE;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import vn.com.notification.infra.messaging.EventType;
import vn.com.notification.infra.messaging.KafkaEventProducer;
import vn.com.notification.infra.messaging.eventmodel.common.BaseEvent;
import vn.com.notification.infra.messaging.eventmodel.common.PayloadEvent;

@Service
@RequiredArgsConstructor
public class CampaignEventProducer implements KafkaEventProducer {
    @Qualifier(CAMPING_KAFKA_TEMPLATE)
    private final KafkaTemplate<String, PayloadEvent> kafkaTemplate;

    @Override
    public List<EventType> supportEventType() {
        return List.of(EventType.CAMPAIGN_EVENT);
    }

    @Override
    public <T extends BaseEvent> void publishEvent(T event, EventType eventType) {
        kafkaTemplate.send(mapToEventObject(event, eventType));
    }
}
