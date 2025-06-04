package vn.com.notification.infra.messaging.eventmodel.common;

import vn.com.notification.infra.messaging.EventType;

public interface BaseEvent {
    String getKey();

    EventType getEventType();
}
