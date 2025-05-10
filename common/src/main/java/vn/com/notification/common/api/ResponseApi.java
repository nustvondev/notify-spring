package vn.com.notification.common.api;

import static vn.com.notification.common.constants.ApplicationConstants.X_REQUEST_ID;

import java.util.Collections;
import java.util.List;
import org.slf4j.MDC;

public record ResponseApi<T>(ResponseStatus status, T payload, ResponseMeta meta) {
    private static final String SUCCESS = "SUCCESS";

    public ResponseApi(ResponseStatus status, ResponseMeta meta) {
        this(status, null, meta);
    }

    public static <T> ResponseApi<T> success(ResponseStatus status, T payload) {
        return success(status, payload, ResponseMeta.fromRequestId(MDC.get(X_REQUEST_ID)));
    }

    public static <T> ResponseApi<T> success(T payload) {
        return success(new ResponseStatus(SUCCESS), payload);
    }

    public static <T> ResponseApi<T> success(T payload, ResponseMeta meta) {
        return new ResponseApi<>(new ResponseStatus(SUCCESS), payload, meta);
    }

    public static <T> ResponseApi<T> success(ResponseStatus status, T payload, ResponseMeta meta) {
        ResponseMeta finalMeta =
                meta != null ? meta : ResponseMeta.fromRequestId(MDC.get(X_REQUEST_ID));
        return new ResponseApi<>(status, payload, finalMeta);
    }

    public static <T> ResponseApi<T> error(String errorCode, String errorMessage) {
        return error(errorCode, errorMessage, Collections.emptyList());
    }

    public static <T> ResponseApi<T> error(
            String errorCode, String errorMessage, List<FieldError> fieldErrors) {
        ResponseStatus responseStatus = new ResponseStatus(errorCode, errorMessage, fieldErrors);
        return new ResponseApi<>(responseStatus, ResponseMeta.fromRequestId(MDC.get(X_REQUEST_ID)));
    }

    public static <T> ResponseApi<T> error(String errorCode, String errorMessage, T payload) {
        ResponseStatus responseStatus = new ResponseStatus(errorCode, errorMessage, null);
        return new ResponseApi<>(
                responseStatus, payload, ResponseMeta.fromRequestId(MDC.get(X_REQUEST_ID)));
    }
}
