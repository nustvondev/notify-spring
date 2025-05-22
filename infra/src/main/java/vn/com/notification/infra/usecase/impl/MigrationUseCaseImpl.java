package vn.com.notification.infra.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import vn.com.notification.core.service.NotificationTemplateService;
import vn.com.notification.core.usecase.MigrationUseCase;

@Service
// @RequiredArgsConstructor
@Slf4j
// @Component
public class MigrationUseCaseImpl implements MigrationUseCase {
    private final NotificationTemplateService notificationTemplateService;
    public MigrationUseCaseImpl(NotificationTemplateService notificationTemplateService){
        this.notificationTemplateService = notificationTemplateService;
    }
    @Override
    public void migrationNotificationTemplate() {
        notificationTemplateService.upsertTemplate();
    }
}
