package vn.com.notification.common.api;

public record FieldError(String field, String message) {
    // No need for explicit constructor or accessors - record handles them
}
