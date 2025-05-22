package vn.com.notification.infra.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.com.notification.core.configuration.NotificationProperties;
import vn.com.notification.core.service.NotificationTemplateService;
import vn.com.notification.infra.repository.dao.NotificationTemplateRepository;
import vn.com.notification.infra.repository.entity.NotificationTemplateEntity;

@Service
// @RequiredArgsConstructor
@Slf4j
public class NotificationTemplateServiceImpl implements NotificationTemplateService {
    private final NotificationTemplateRepository notificationTemplateRepository;
    private final NotificationProperties notificationProperties;

    public NotificationTemplateServiceImpl(NotificationTemplateRepository notificationTemplateRepository,
    NotificationProperties notificationProperties) {
        this.notificationProperties = notificationProperties;
        this.notificationTemplateRepository = notificationTemplateRepository;
    }


    @Override
    public void upsertTemplate() {
        if (isTemplateRepositoryEmpty()) {
            initializeTemplates();
        } else {
            log.info("Notification templates already exist");
        }
    }

    private boolean isTemplateRepositoryEmpty() {
        return notificationTemplateRepository.count() == 0;
    }

    private void initializeTemplates() {
        notificationProperties.getTemplates().forEach(this::createAndSaveTemplate);
    }

    private void createAndSaveTemplate(NotificationProperties.Template template) {
        NotificationTemplateEntity entity = createTemplateEntity(template);
        notificationTemplateRepository.save(entity);
        log.info("Initialized notification template: {}", template.getCode());
    }

    private NotificationTemplateEntity createTemplateEntity(
            NotificationProperties.Template template) {
        NotificationTemplateEntity entity = new NotificationTemplateEntity();
        entity.setCode(template.getCode());
        entity.setTitle(template.getTitle());
        entity.setContent(template.getContent());
        entity.setHtmlContent(template.getHtmlContent());
        entity.setSmsContent(template.getSmsContent());
        entity.setType(template.getType());
        return entity;
    }
}
