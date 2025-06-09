package vn.com.notification.infra.repository.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.notification.infra.repository.dao.entity.NotificationTemplateEntity;

public interface NotificationTemplateRepository
        extends JpaRepository<NotificationTemplateEntity, Long> {
    Optional<NotificationTemplateEntity> findByCode(String code);
}
