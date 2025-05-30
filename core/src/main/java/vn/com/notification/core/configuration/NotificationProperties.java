package vn.com.notification.core.configuration;

import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "notification")
public class NotificationProperties {
    private List<Template> templates;

    @Data
    public static class Template {
        private String code;
        private String title;
        private String content;
        private String htmlContent;
        private String smsContent;
        private String type;
    }
}
