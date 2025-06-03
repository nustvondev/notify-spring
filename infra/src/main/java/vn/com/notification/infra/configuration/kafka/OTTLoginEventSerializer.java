package vn.com.notification.infra.configuration.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.stereotype.Component;
import vn.com.notification.infra.messaging.eventmodel.ott.OTTLoginEventPayload;

@Component
@RequiredArgsConstructor
@Slf4j
public class OTTLoginEventSerializer implements Serializer<OTTLoginEventPayload> {
    private final ObjectMapper objectMapper;

    @Override
    public byte[] serialize(String topic, OTTLoginEventPayload data) {
        try {
            if (data == null) {
                log.debug("Null received at serializing");
                return new byte[0];
            }
            log.debug("Serializing...");
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {

            log.error("Error when serializing OTTLoginEventPayload to byte[]", e);
            throw new RuntimeException("Error when serializing OTTLoginEventPayload to byte[]", e);
        }
    }
}
