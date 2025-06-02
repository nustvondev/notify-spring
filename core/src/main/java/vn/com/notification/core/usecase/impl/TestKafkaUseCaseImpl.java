package vn.com.notification.core.usecase.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vn.com.notification.core.service.TestKafkaService;
import vn.com.notification.core.usecase.TestKafkaUseCase;

@RequiredArgsConstructor
@Slf4j
@Component
public class TestKafkaUseCaseImpl implements TestKafkaUseCase {
    private final TestKafkaService testKafkaService;

    @Override
    public void testKafkaProducer() {
        testKafkaService.testKafkaProducer();
    }
}
