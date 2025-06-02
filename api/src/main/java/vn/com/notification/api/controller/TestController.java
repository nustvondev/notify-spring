package vn.com.notification.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.notification.core.usecase.TestKafkaUseCase;

@RestController
@RequestMapping("/v1")
@Slf4j
public class TestController {
    private final TestKafkaUseCase testKafkaUseCase;

    public TestController(TestKafkaUseCase testKafkaUseCase) {
        this.testKafkaUseCase = testKafkaUseCase;
    }

    @RequestMapping("/test")
    public String test() {
        log.info("Test endpoint called");
        testKafkaUseCase.testKafkaProducer();
        return "Test successful";
    }
}
