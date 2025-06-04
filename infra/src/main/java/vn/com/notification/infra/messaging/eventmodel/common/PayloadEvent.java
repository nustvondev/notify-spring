package vn.com.notification.infra.messaging.eventmodel.common;

import java.time.Instant;
import vn.com.notification.infra.messaging.EventType;

public record PayloadEvent(
        String eventId, EventType eventType, Instant eventTime, Object payload) {}
