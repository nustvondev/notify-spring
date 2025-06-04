package vn.com.notification.infra.messaging.eventmodel.ott;

import vn.com.notification.infra.messaging.EventType;
import vn.com.notification.infra.messaging.eventmodel.common.BaseEvent;

public record CampaignEventPayload(
        String userId, String code, String content, String contentHtml, String smsContent, String type)
        implements BaseEvent {

    @Override
    public String getKey() {
        //        return MDC.get(X_REQUEST_ID);
        return String.valueOf(userId);
    }

    @Override
    public EventType getEventType() {
        return EventType.CAMPAIGN_EVENT; // Return the appropriate event type
    }
}
