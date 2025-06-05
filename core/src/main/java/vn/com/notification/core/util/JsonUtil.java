package vn.com.notification.core.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.util.*;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@UtilityClass
@Slf4j
public class JsonUtil {

    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        mapper.registerModule(new JavaTimeModule());
    }

    public static ObjectMapper objectMapper() {
        return mapper;
    }

    public static <T> String objectToJson(T src) {
        if (src == null) return null;
        try {
            return mapper.writeValueAsString(src);
        } catch (Exception e) {
            log.error("Error serializing object: {}", src, e);
            return null;
        }
    }

    public static <T> T jsonToObject(String json, Class<T> valueType) {
        if (StringUtils.isEmpty(json)) return null;
        try {
            return mapper.readValue(json, valueType);
        } catch (Exception e) {
            log.error("Error deserializing to {}: {}", valueType.getSimpleName(), json, e);
            return null;
        }
    }

    public static <T> T jsonToObject(String json, TypeReference<T> valueTypeRef) {
        if (StringUtils.isEmpty(json)) return null;
        try {
            return mapper.readValue(json, valueTypeRef);
        } catch (Exception e) {
            log.error("Error deserializing to TypeReference: {}", json, e);
            return null;
        }
    }

    public static JsonNode parseStringToJsonNode(String data) {
        if (StringUtils.isEmpty(data)) return null;
        try {
            return mapper.readTree(data);
        } catch (IOException e) {
            log.error("Failed to parse string to JsonNode: {}", data, e);
            return mapper.createObjectNode();
        }
    }

    public static JsonNode parseByteToJsonNode(byte[] data) {
        if (ObjectUtils.isEmpty(data)) return null;
        try {
            return mapper.readTree(data);
        } catch (IOException e) {
            log.error("Failed to parse bytes to JsonNode", e);
            return mapper.createObjectNode();
        }
    }

    public static JsonNode parseObjectToJsonNode(Object object) {
        if (object instanceof String) return null;
        try {
            return mapper.convertValue(object, JsonNode.class);
        } catch (IllegalArgumentException e) {
            log.error("Failed to convert object to JsonNode: {}", object, e);
            return mapper.createObjectNode();
        }
    }

    public static Map<String, Object> flattenJson(JsonNode node, String prefix) {
        Map<String, Object> map = new HashMap<>();
        if (node.isObject()) {
            node.fields()
                    .forEachRemaining(
                            entry -> {
                                String newKey = prefix.isEmpty() ? entry.getKey() : prefix + "." + entry.getKey();
                                if (entry.getValue().isContainerNode()) {
                                    map.putAll(flattenJson(entry.getValue(), newKey));
                                } else {
                                    map.put(newKey, entry.getValue().asText());
                                }
                            });
        } else if (node.isArray()) {
            int index = 0;
            for (JsonNode element : node) {
                String newKey = prefix + "." + index++;
                if (element.isContainerNode()) {
                    map.putAll(flattenJson(element, newKey));
                } else {
                    map.put(newKey, element.asText());
                }
            }
        }
        return map;
    }

    public static Map<String, Object> convertJsonNodeToMap(JsonNode jsonNode) {
        if (!jsonNode.isObject()) {
            throw new IllegalArgumentException("Input must be a JSON object");
        }
        Map<String, Object> result = new LinkedHashMap<>();
        jsonNode
                .fields()
                .forEachRemaining(entry -> result.put(entry.getKey(), convertJsonValue(entry.getValue())));
        return result;
    }

    public static List<Object> convertJsonArrayToList(JsonNode arrayNode) {
        if (!arrayNode.isArray()) {
            throw new IllegalArgumentException("Input must be a JSON array");
        }
        List<Object> list = new ArrayList<>();
        for (JsonNode node : arrayNode) {
            list.add(convertJsonValue(node));
        }
        return list;
    }

    private static Object convertJsonValue(JsonNode node) {
        if (node.isObject()) return convertJsonNodeToMap(node);
        if (node.isArray()) return convertJsonArrayToList(node);
        if (node.isInt()) return node.intValue();
        if (node.isLong()) return node.longValue();
        if (node.isDouble()) return node.doubleValue();
        if (node.isTextual()) return node.textValue();
        if (node.isBoolean()) return node.booleanValue();
        if (node.isNull()) return null;
        return node.asText(); // fallback
    }
}
