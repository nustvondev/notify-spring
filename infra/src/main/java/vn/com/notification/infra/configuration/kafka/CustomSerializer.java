package vn.com.notification.infra.configuration.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.stereotype.Component;
import vn.com.notification.infra.messaging.eventmodel.login.PayloadEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomSerializer implements Serializer<PayloadEvent> {
    private final ObjectMapper objectMapper;

    @Override
    public byte[] serialize(String topic, PayloadEvent data) {
        try {
            if (data == null) {
                log.debug("Null received at serializing");
                return new byte[0];
            }
            log.debug("Serializing...");
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            //            throw new ServerSideException(
            //                    ErrorCode.SERVER_ERROR.getCode(), "Error when serializing MessageDto to
            // byte[]");
            log.error("Error when serializing MessageDto to byte[]", e);
            throw new RuntimeException("Error when serializing MessageDto to byte[]", e);
        }
    }
}
