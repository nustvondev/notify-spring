package vn.com.notification.infra.configuration.kafka;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "kafka")
public class KafkaConfigurationProperties {
    private List<KafkaProducer> producers = new ArrayList<>();
    private Consumer consumers;
    private String server;
    private String protocol;
    private Topic topics;

    @Getter
    @Setter
    public static class KafkaProducer {
        private String topic;
        private String ack;
        private boolean enableIdempotence = true;
        private Integer retry;
        private Integer maxInFlightRequestPerConnection;
        private Long retryBackoffMs;
        private Long requestTimeoutMs;
        private Long deliveryTimeoutMs;
    }

    @Getter
    @Setter
    public static class Topic {
        private String ottMessageLogin;
    }

    @Getter
    @Setter
    private static class Consumer {

        private KafkaConsumer onboarding;
        private Map<String, String> properties;

        @Getter
        @Setter
        public static class KafkaConsumer {
            private String groupId;
            private String topic;
        }
    }
}
