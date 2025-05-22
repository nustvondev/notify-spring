package vn.com.notification.api.filter;

import static vn.com.notification.common.constants.ApplicationConstants.X_REQUEST_ID;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Slf4j
// @RequiredArgsConstructor
@Component
public class TrackingRequestFilter extends OncePerRequestFilter {
    private static final String HEADERS_STRING_FORMAT = "%s = %s; ";

    @Value("${application.max-length-payload-log}")
    private int maxLengthPayloadLog;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (isHealthCheckRequest(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        CachedHttpServletRequest requestWrapper = new CachedHttpServletRequest(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        String payload = getRequestPayload(requestWrapper, request);

        logRequest(payload, requestWrapper);
        executeFilter(payload, requestWrapper, responseWrapper, filterChain);
        log.info(
                "Completed incoming request to service: {} {}",
                request.getMethod(),
                request.getRequestURI());
    }

    private boolean isHealthCheckRequest(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        return "/api/notification-service/actuator/health".equals(requestUri)
                || "/api/notification-service/actuator/prometheus".equals(requestUri)
                || "/api/notification-service/metrics".equals(requestUri);
    }

    private String getRequestPayload(
            CachedHttpServletRequest requestWrapper, HttpServletRequest request) throws IOException {
        return IOUtils.toString(requestWrapper.getInputStream(), request.getCharacterEncoding());
    }

    @SneakyThrows
    private void logRequest(String payload, HttpServletRequest request) {
        log.info("Incoming request to service: {} {}", request.getMethod(), request.getRequestURI());
        log.info(X_REQUEST_ID + ": {}", request.getHeader(X_REQUEST_ID));
        log.info("Query Params: [{}]", request.getQueryString());
        log.debug("Headers: [{}]", buildHeaders(request));
    }

    private String buildHeaders(HttpServletRequest request) {
        Iterator<String> headers = request.getHeaderNames().asIterator();

        StringBuilder headersString = new StringBuilder();
        while (headers.hasNext()) {
            String headerName = headers.next();
            headersString.append(
                    String.format(HEADERS_STRING_FORMAT, headerName, request.getHeader(headerName)));
        }

        return headersString.toString();
    }

    @SneakyThrows
    private void executeFilter(
            String payload,
            CachedHttpServletRequest request,
            ContentCachingResponseWrapper responseWrapper,
            FilterChain filterChain) {
        try {
            filterChain.doFilter(request, responseWrapper);
        } finally {
            handleResponseLogging(responseWrapper);
            responseWrapper.copyBodyToResponse();
        }
    }

    private void handleResponseLogging(ContentCachingResponseWrapper responseWrapper)
            throws IOException {
        byte[] responseArray = responseWrapper.getContentAsByteArray();
        String responseStr = new String(responseArray, responseWrapper.getCharacterEncoding());
        if (responseWrapper.getStatus() != 200) {
            log.error(
                    "has an error occur with status [{}] and response [{}]",
                    responseWrapper.getStatus(),
                    responseStr);
        }
    }
}
