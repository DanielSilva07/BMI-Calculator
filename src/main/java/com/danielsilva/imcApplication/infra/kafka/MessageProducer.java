package com.danielsilva.imcApplication.infra.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {
    
    private static final Logger logger = LoggerFactory.getLogger(MessageProducer.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public MessageProducer(KafkaTemplate<String, String> kafkaTemplate,
                           ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper != null ? objectMapper : new ObjectMapper();
    }

    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
        logger.info("Mensagem enviada para o tópico {}: {}", topic, message);
    }
    
    public <T> void sendMessage(String topic, T object) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(object);
            kafkaTemplate.send(topic, jsonMessage);
            logger.info("Objeto JSON enviado para o tópico {}: {}", topic, jsonMessage);
        } catch (JsonProcessingException e) {
            logger.error("Erro ao serializar objeto para JSON", e);
            throw new RuntimeException("Falha ao serializar mensagem para JSON", e);
        }
    }
}
