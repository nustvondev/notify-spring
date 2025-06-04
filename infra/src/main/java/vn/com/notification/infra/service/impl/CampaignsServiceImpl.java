package vn.com.notification.infra.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.com.notification.core.dto.params.CampaignCreationParams;
import vn.com.notification.core.dto.result.CampaignCreationResult;
import vn.com.notification.core.service.CampaignsService;
import vn.com.notification.infra.messaging.KafkaEventService;
import vn.com.notification.infra.messaging.eventmodel.ott.CampaignEventPayload;
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
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        template.ifPresentOrElse(
                entity -> {
                    try {
                        String templateJson = objectMapper.writeValueAsString(entity);
                        log.info("Notification template found for code {}: {}", params.getCode(), templateJson);
                        List<String> userIds = generateRandomUserIds(100);
                        for (String userId : userIds) {
                            kafkaEventService.publishEvent(
                                    new CampaignEventPayload(
                                            userId,
                                            entity.getCode(),
                                            entity.getContent(),
                                            entity.getHtmlContent(),
                                            entity.getSmsContent(),
                                            entity.getType()));
                        }
                    } catch (Exception e) {
                        log.error("Failed to convert template to JSON", e);
                    }
                },
                () -> log.error("Notification template not found for code: {}", params.getCode()));

        return template
                .map(entity -> new CampaignCreationResult(entity.getCode(), entity.getContent()))
                .orElseGet(() -> new CampaignCreationResult("false", "Notification template not found"));
    }

    public List<String> generateRandomUserIds(int count) {
        // Validate input
        if (count <= 0) {
            throw new IllegalArgumentException("Count must be positive");
        }

        Random random = new Random();
        final String prefix = "userId";

        return IntStream.range(0, count)
                .mapToObj(
                        i -> {
                            // Generate random number between 1000 and 9999 (adjust range as needed)
                            int randomNumber = 1000 + random.nextInt(9000);
                            return prefix + randomNumber;
                        })
                .collect(Collectors.toList());
    }
}
