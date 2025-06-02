package vn.com.notification.infra.messaging;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.slf4j.MDC;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import vn.com.notification.infra.messaging.eventmodel.login.BaseEvent;
import vn.com.notification.infra.messaging.eventmodel.login.PayloadEvent;
import vn.com.notification.infra.messaging.eventmodel.ott.OTTLoginEventPayload;

public interface KafkaEventProducer {
    String X_REQUEST_ID = "X-Request-ID";
    String SERVICE_NAME = "Service-Name";
    String AUTHENTICATION_SERVICE = "Authentication-Service";

    List<EventType> supportEventType();

    <T extends BaseEvent> void publishEvent(T event, EventType eventType);

    default <T extends BaseEvent> Message<PayloadEvent> mapToEventObject(
            T payload, EventType eventType) {
        PayloadEvent baseEvent =
                new PayloadEvent(UUID.randomUUID().toString(), eventType, Instant.now(), payload);
        var builder =
                MessageBuilder.withPayload(baseEvent)
                        .setHeader(SERVICE_NAME, AUTHENTICATION_SERVICE)
                        .setHeader(X_REQUEST_ID, MDC.get(X_REQUEST_ID))
                        .setHeader(KafkaHeaders.KEY, payload.getKey());

        return builder.build();
    }

    default <T extends BaseEvent> Message<OTTLoginEventPayload> mapToNotificationObject(
            OTTLoginEventPayload payload) {
        var uuid = UUID.randomUUID().toString();
        var builder =
                MessageBuilder.withPayload(payload)
                        .setHeader(SERVICE_NAME, AUTHENTICATION_SERVICE)
                        .setHeader(X_REQUEST_ID, uuid)
                        .setHeader(KafkaHeaders.KEY, uuid);
        return builder.build();
    }
}
