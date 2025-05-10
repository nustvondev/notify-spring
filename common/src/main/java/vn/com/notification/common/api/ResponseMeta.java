package vn.com.notification.common.api;

public record ResponseMeta(String requestId, String nextCursor) {
    public static ResponseMeta fromRequestId(String requestId) {
        return new ResponseMeta(requestId, null);
    }
}
