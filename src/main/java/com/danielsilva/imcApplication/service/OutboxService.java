package com.danielsilva.imcApplication.service;

import com.danielsilva.imcApplication.domain.Outbox;
import com.danielsilva.imcApplication.infra.repository.OutboxRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;


@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxService {

    private final OutboxRepository outboxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private static final String TOPIC = "imc";

    @Transactional
    public void saveToOutbox(Object payload, String aggregateId) {
        try {
            Outbox outbox = new Outbox();
            outbox.setAggregateId(aggregateId);
            outbox.setPayload(objectMapper.writeValueAsString(payload));
            outboxRepository.save(outbox);
            log.info("Message saved to outbox with id: {}", outbox.getId());
        } catch (JsonProcessingException e) {
            log.error("Error converting payload to JSON", e);
            throw new RuntimeException("Failed to save to outbox", e);
        }
    }

    /**
     * Processes pending outbox messages and sends them to Kafka.
     * Runs every 5 seconds and processes messages in batches of 100.
     */
    @Scheduled(fixedRate = 5000) // Runs every 5 seconds
    @Transactional
    public void processOutbox() {
        // Process messages in batches of 100
        Pageable pageable = PageRequest.of(0, 100);
        Page<Outbox> pendingMessages = outboxRepository.findByProcessedFalse(pageable);
        
        log.info("Found {} pending messages to process", pendingMessages.getNumberOfElements());
        
        pendingMessages.forEach(outbox -> {
            try {
                // Send message and wait for acknowledgment (synchronously)
                SendResult<String, String> result = kafkaTemplate.send(TOPIC, outbox.getPayload())
                    .get(10, TimeUnit.SECONDS);
                
                // If we get here, the message was sent successfully
                log.info("Message sent successfully to topic: {} with offset: {}", 
                    TOPIC, result.getRecordMetadata().offset());
                
                // Update outbox status in the same transaction
                outbox.setProcessed(true);
                outbox.setProcessedAt(LocalDateTime.now());
                outboxRepository.save(outbox);
                
                log.info("Outbox message marked as processed: {}", outbox.getId());
                
            } catch (Exception e) {
                log.error("Failed to process outbox message: {}", outbox.getId(), e);
                // The transaction will be rolled back if an exception occurs
            }
        });
    }
}