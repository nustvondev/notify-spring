package vn.com.notification.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/health")
@RequiredArgsConstructor
@Slf4j
public class NotificationCheckHealthController {

    @RequestMapping()
    public String healthCheck() {
        return "Notification service is up and running!";
    }
}
