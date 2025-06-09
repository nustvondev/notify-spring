package vn.com.notification.infra.repository.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@Entity
@Table(name = "notification")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners({AuditingEntityListener.class})
public class NotificationEntity extends BaseEntity {

    @Column(name = "user_id", columnDefinition = "TEXT")
    private String userId;

    @Column(name = "template_id", columnDefinition = "TEXT")
    private String templateId;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "html_content", columnDefinition = "TEXT")
    private String htmlContent;

    @Column(name = "sms_content", columnDefinition = "TEXT")
    private String smsContent;

    @Column(name = "is_read", nullable = false)
    private boolean isRead = false;

    @Column(name = "meta", columnDefinition = "TEXT")
    private String meta;

    @Column(name = "read_at")
    private LocalDateTime readAt;
}
