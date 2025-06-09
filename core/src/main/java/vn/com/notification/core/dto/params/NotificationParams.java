package vn.com.notification.core.dto.params;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class NotificationParams {
    private String userId;
    private String code;
    private String title;
    private String content;
    private String contentHtml;
    private String smsContent;
    private boolean isRead = false;
    private String meta;
    private LocalDateTime readAt;
}
