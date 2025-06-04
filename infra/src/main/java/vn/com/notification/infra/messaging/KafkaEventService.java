package vn.com.notification.infra.messaging;

import java.util.Map;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.com.notification.infra.messaging.eventmodel.common.BaseEvent;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaEventService {
    private final Map<EventType, KafkaEventProducer> producerMap;

    public <T extends BaseEvent> void publishEvent(@NonNull T event) {
        Optional.ofNullable(producerMap.get(event.getEventType()))
                .map(
                        kafkaEventProducer -> {
                            kafkaEventProducer.publishEvent(event, event.getEventType());
                            return kafkaEventProducer;
                        })
                .orElseThrow(
                        () -> {
                            log.error("Not support eventType: {}", event.getEventType());
                            return new IllegalArgumentException(
                                    String.format("Not support eventType: %s", event.getEventType()));
                        });
    }
}
