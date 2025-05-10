package vn.com.notification.core.enumeration;

import java.util.Arrays;
import java.util.Objects;
import lombok.Getter;
import vn.com.notification.core.domain.ApplicationConstant;

@Getter
public enum Modifier {
    INTERNAL_SYSTEM(ApplicationConstant.INTERNAL_REQUEST_PATTERN),
    ADMIN(ApplicationConstant.ADMIN_REQUEST_PATTERN),
    SYSTEM(null);

    private final String requestPattern;

    Modifier(String requestPattern) {
        this.requestPattern = requestPattern;
    }

    public static Modifier from(String requestPattern) {
        return Arrays.stream(Modifier.values())
                .filter(modifier -> Objects.equals(modifier.getRequestPattern(), requestPattern))
                .findFirst()
                .orElse(Modifier.SYSTEM);
    }
}
