package vn.com.notification.infra.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.notification.infra.repository.entity.NotificationEntity;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {}
