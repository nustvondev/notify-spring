package vn.com.notification.infra.messaging.eventmodel.ott;

import static vn.com.notification.infra.messaging.KafkaEventProducer.X_REQUEST_ID;

import org.slf4j.MDC;
import vn.com.notification.infra.messaging.EventType;
import vn.com.notification.infra.messaging.eventmodel.common.BaseEvent;

public record OTTLoginEventPayload(String eventType, String language
        //        Map<String, String> data,
        //        Map<String, Object> additionalData
        ) implements BaseEvent {

    @Override
    public String getKey() {
        return MDC.get(X_REQUEST_ID);
    }

    @Override
    public EventType getEventType() {
        return EventType.OTT_LOGIN;
    }
}
