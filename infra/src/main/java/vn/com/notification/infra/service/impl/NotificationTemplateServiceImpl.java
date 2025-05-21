package vn.com.notification.infra.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.com.notification.core.configuration.NotificationProperties;
import vn.com.notification.core.service.NotificationTemplateService;
import vn.com.notification.infra.repository.dao.NotificationTemplateRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationTemplateServiceImpl implements NotificationTemplateService {
    private final NotificationTemplateRepository notificationTemplateRepository;
    private final NotificationProperties notificationProperties;

    @Override
    public void upsertTemplate() {}
}
