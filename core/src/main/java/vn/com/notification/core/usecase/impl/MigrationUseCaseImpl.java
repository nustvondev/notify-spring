package vn.com.notification.core.usecase.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import vn.com.notification.core.service.NotificationTemplateService;
import vn.com.notification.core.usecase.MigrationUseCase;

@RequiredArgsConstructor
@Slf4j
@Component
public class MigrationUseCaseImpl implements MigrationUseCase {
    private final NotificationTemplateService notificationTemplateService;

    @Override
    public void migrationNotificationTemplate() {
        notificationTemplateService.upsertTemplate();
    }
}
