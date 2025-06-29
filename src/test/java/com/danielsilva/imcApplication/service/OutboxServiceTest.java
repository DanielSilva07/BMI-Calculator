package com.danielsilva.imcApplication.service;


import java.util.List;
import java.util.concurrent.CompletableFuture;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.danielsilva.imcApplication.domain.Outbox;
import com.danielsilva.imcApplication.infra.repository.OutboxRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OutboxServiceTest {

    @Mock
    private OutboxRepository outboxRepository;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private OutboxService outboxService;

    private Outbox outbox;

    @BeforeEach
    void setUp() {
        outbox = new Outbox();
        outbox.setId(UUID.randomUUID());
        outbox.setAggregateId("123");
        outbox.setPayload("test-payload");
        outbox.setProcessed(false);
    }

    @Test
    void shouldSaveMessageToDatabase() throws Exception {
        Object testPayload = new Object();
        when(objectMapper.writeValueAsString(any())).thenReturn("test-payload");

        outboxService.saveToOutbox(testPayload, "123");

        verify(objectMapper).writeValueAsString(testPayload);
        verify(outboxRepository).save(any(Outbox.class));
    }


     @Test
    void shouldNotMessageToKafka() throws Exception {
         when(outboxRepository.findByProcessedFalse(any(Pageable.class))).thenReturn(Page.empty());

         outboxService.processOutbox();

         verify(outboxRepository, times(1)).findByProcessedFalse(any(Pageable.class));
         verify(kafkaTemplate, never()).send(any(ProducerRecord.class));
     }


    @Test
    void shouldProcessMessageSuccessfully() throws Exception {
        Page<Outbox> page = new PageImpl<>(Collections.singletonList(outbox));
        when(outboxRepository.findByProcessedFalse(any(Pageable.class))).thenReturn(page);

        // Mock do RecordMetadata
        RecordMetadata recordMetadata = mock(RecordMetadata.class);
        SendResult<String, String> sendResult = mock(SendResult.class);
        when(sendResult.getRecordMetadata()).thenReturn(recordMetadata);
        when(recordMetadata.offset()).thenReturn(0L);

        // Configura o mock para retornar um futuro completado
        CompletableFuture<SendResult<String, String>> future = CompletableFuture.completedFuture(sendResult);
        when(kafkaTemplate.send(eq("imc"), eq(outbox.getPayload())))
                .thenReturn(future);

        outboxService.processOutbox();

        verify(kafkaTemplate).send(eq("imc"), eq(outbox.getPayload()));
        verify(outboxRepository).save(outbox);
        assertTrue(outbox.isProcessed());
        assertNotNull(outbox.getProcessedAt());
    }

    @Test
    void shouldWhenKafkaFailsShouldLogErrorAndNotUpdateOutbox() throws Exception {
        Page<Outbox> page = new PageImpl<>(Collections.singletonList(outbox));
        when(outboxRepository.findByProcessedFalse(any(Pageable.class))).thenReturn(page);

        // Configura o mock para falhar
        CompletableFuture<SendResult<String, String>> failedFuture = new CompletableFuture<>();
        failedFuture.completeExceptionally(new RuntimeException("Kafka error"));
        when(kafkaTemplate.send(eq("imc"), eq(outbox.getPayload())))
                .thenReturn(failedFuture);

        outboxService.processOutbox();

        verify(kafkaTemplate).send(eq("imc"), eq(outbox.getPayload()));
        verify(outboxRepository, never()).save(any(Outbox.class));
        assertFalse(outbox.isProcessed());
        assertNull(outbox.getProcessedAt());
    }

    @Test
    void shouldWhenTimeoutOccursShouldLogErrorAndNotUpdateOutbox() throws Exception {
        Page<Outbox> page = new PageImpl<>(Collections.singletonList(outbox));
        when(outboxRepository.findByProcessedFalse(any(Pageable.class))).thenReturn(page);

        // Configura o mock para simular timeout
        CompletableFuture<SendResult<String, String>> timeoutFuture = new CompletableFuture<>();
        when(kafkaTemplate.send(eq("imc"), eq(outbox.getPayload())))
                .thenReturn(timeoutFuture);

        // Não completamos o future, então o get() lançará TimeoutException

        outboxService.processOutbox();

        verify(kafkaTemplate).send(eq("imc"), eq(outbox.getPayload()));
        verify(outboxRepository, never()).save(any(Outbox.class));
        assertFalse(outbox.isProcessed());
        assertNull(outbox.getProcessedAt());
    }

    @Test
    void shouldWhenNoMessagesShouldNotCallKafka() {
        when(outboxRepository.findByProcessedFalse(any(Pageable.class)))
                .thenReturn(Page.empty());

        outboxService.processOutbox();

        verify(kafkaTemplate, never()).send(anyString(), anyString());
        verify(outboxRepository, never()).save(any(Outbox.class));
    }

    @Test
    void shouldWhenMultipleMessagesShouldProcessAll() throws Exception {
        Outbox outbox2 = new Outbox();
        outbox2.setId(UUID.randomUUID());
        outbox2.setPayload("another-payload");
        outbox2.setProcessed(false);

        Page<Outbox> page = new PageImpl<>(List.of(outbox, outbox2));
        when(outboxRepository.findByProcessedFalse(any(Pageable.class))).thenReturn(page);

        // Mock do RecordMetadata
        RecordMetadata recordMetadata = mock(RecordMetadata.class);
        SendResult<String, String> sendResult = mock(SendResult.class);
        when(sendResult.getRecordMetadata()).thenReturn(recordMetadata);
        when(recordMetadata.offset()).thenReturn(0L);

        // Configura o mock para retornar um futuro completado
        CompletableFuture<SendResult<String, String>> future = CompletableFuture.completedFuture(sendResult);
        when(kafkaTemplate.send(anyString(), anyString())).thenReturn(future);

        outboxService.processOutbox();

        verify(kafkaTemplate, times(2)).send(anyString(), anyString());
        verify(outboxRepository, times(2)).save(any(Outbox.class));
        assertTrue(outbox.isProcessed());
        assertTrue(outbox2.isProcessed());
    }
}


