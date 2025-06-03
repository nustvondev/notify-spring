package vn.com.notification.infra.configuration.kafka;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import vn.com.notification.infra.messaging.EventType;
import vn.com.notification.infra.messaging.KafkaEventProducer;
import vn.com.notification.infra.messaging.eventmodel.login.PayloadEvent;
import vn.com.notification.infra.messaging.eventmodel.ott.OTTLoginEventPayload;

@Configuration
public class KafkaEventProducerConfig {
    public static final String OTT_LOGIN_KAFKA_TEMPLATE = "ott-login-template";

    @Bean
    Map<EventType, KafkaEventProducer> provideKafkaEventProducer(
            List<KafkaEventProducer> kafkaEventProducers) {
        return kafkaEventProducers.stream()
                .flatMap(
                        kafkaEventProducer ->
                                kafkaEventProducer.supportEventType().stream()
                                        .collect(Collectors.toMap(Function.identity(), unused -> kafkaEventProducer))
                                        .entrySet()
                                        .stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /** Create an instance supplied producer by topic. */
    @Bean(OTT_LOGIN_KAFKA_TEMPLATE)
    public KafkaTemplate<String, OTTLoginEventPayload> ottLoginKafkaTemplate(
            KafkaConfigurationProperties configuration, OTTLoginEventSerializer customSerializer) {
        String topic = configuration.getTopics().getOttMessageLogin();
        KafkaTemplate<String, OTTLoginEventPayload> kafkaTemplate =
                new KafkaTemplate<>(producerNotificationFactory(configuration, topic, customSerializer));
        kafkaTemplate.setDefaultTopic(topic);
        return kafkaTemplate;
    }

    private ProducerFactory<String, PayloadEvent> producerFactory(
            KafkaConfigurationProperties configuration, String topic, CustomSerializer customSerializer) {
        KafkaConfigurationProperties.KafkaProducer producer =
                findKafkaProducerByTopic(configuration, topic);
        Map<String, Object> configs = new HashMap<>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, configuration.getServer());
        configs.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, configuration.getProtocol());
        configs.put(
                ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, String.valueOf(producer.isEnableIdempotence()));
        configs.put(ProducerConfig.ACKS_CONFIG, producer.getAck());
        configs.put(ProducerConfig.RETRIES_CONFIG, String.valueOf(producer.getRetry()));
        configs.put(
                ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION,
                String.valueOf(producer.getMaxInFlightRequestPerConnection()));
        configs.put(
                ProducerConfig.RETRY_BACKOFF_MS_CONFIG, String.valueOf(producer.getRetryBackoffMs()));
        configs.put(
                ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, String.valueOf(producer.getRequestTimeoutMs()));
        configs.put(
                ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, String.valueOf(producer.getDeliveryTimeoutMs()));
        return new DefaultKafkaProducerFactory<>(configs, new StringSerializer(), customSerializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory kafkaListenerContainerFactory(
            KafkaConfigurationProperties configuration) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory(configuration));
        return factory;
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory(
            KafkaConfigurationProperties configuration) {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, configuration.getServer());
        config.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, configuration.getProtocol());
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(config);
    }

    private KafkaConfigurationProperties.KafkaProducer findKafkaProducerByTopic(
            KafkaConfigurationProperties configuration, final String topic) {
        return configuration.getProducers().stream()
                .filter(producer -> topic.equalsIgnoreCase(producer.getTopic()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Unexpected value: %s".formatted(topic)));
    }

    private ProducerFactory<String, OTTLoginEventPayload> producerNotificationFactory(
            KafkaConfigurationProperties configuration,
            String topic,
            OTTLoginEventSerializer customSerializer) {
        KafkaConfigurationProperties.KafkaProducer producer =
                findKafkaProducerByTopic(configuration, topic);
        Map<String, Object> configs = new HashMap<>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, configuration.getServer());
        configs.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, configuration.getProtocol());
        configs.put(
                ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, String.valueOf(producer.isEnableIdempotence()));
        configs.put(ProducerConfig.ACKS_CONFIG, producer.getAck());
        configs.put(ProducerConfig.RETRIES_CONFIG, String.valueOf(producer.getRetry()));
        configs.put(
                ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION,
                String.valueOf(producer.getMaxInFlightRequestPerConnection()));
        configs.put(
                ProducerConfig.RETRY_BACKOFF_MS_CONFIG, String.valueOf(producer.getRetryBackoffMs()));
        configs.put(
                ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, String.valueOf(producer.getRequestTimeoutMs()));
        configs.put(
                ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, String.valueOf(producer.getDeliveryTimeoutMs()));
        return new DefaultKafkaProducerFactory<>(configs, new StringSerializer(), customSerializer);
    }
}
