package vn.com.notification.infra.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.com.notification.core.dto.params.CampaignCreationParams;
import vn.com.notification.core.dto.result.CampaignCreationResult;
import vn.com.notification.core.service.CampaignsService;
import vn.com.notification.infra.messaging.KafkaEventService;
import vn.com.notification.infra.repository.dao.NotificationTemplateRepository;
import vn.com.notification.infra.repository.entity.NotificationTemplateEntity;

@Service
@RequiredArgsConstructor
@Slf4j
public class CampaignsServiceImpl implements CampaignsService {
    private final NotificationTemplateRepository notificationTemplateRepository;
    private final KafkaEventService kafkaEventService;

    @Override
    public CampaignCreationResult createCampaign(CampaignCreationParams params) {
        Optional<NotificationTemplateEntity> template =
                notificationTemplateRepository.findByCode(params.getCode());

        // Khởi tạo ObjectMapper với JavaTimeModule
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        template.ifPresentOrElse(
                entity -> {
                    try {
                        String templateJson = objectMapper.writeValueAsString(entity);
                        log.info("Notification template found for code {}: {}", params.getCode(), templateJson);
                    } catch (Exception e) {
                        log.error("Failed to convert template to JSON", e);
                    }
                },
                () -> log.error("Notification template not found for code: {}", params.getCode()));

        return template
                .map(entity -> new CampaignCreationResult("true", "Campaign created successfully"))
                .orElseGet(() -> new CampaignCreationResult("false", "Notification template not found"));
    }
}
