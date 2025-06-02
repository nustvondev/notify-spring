package vn.com.notification.infra.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.com.notification.core.service.TestKafkaService;
import vn.com.notification.infra.messaging.KafkaEventService;
import vn.com.notification.infra.messaging.eventmodel.ott.OTTLoginEventPayload;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestKafkaServiceImpl implements TestKafkaService {

    private final KafkaEventService kafkaEventService;

    @Override
    public void testKafkaProducer() {
        kafkaEventService.publishEvent(new OTTLoginEventPayload("testUser", "testDevice"));
    }
}
