package vn.com.notification.core.dto.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class NotificationTemplateDetail {
    private String code;
    private String title;
    private String content;
    private String htmlContent;
    private String smsContent;
    private String type;
}
