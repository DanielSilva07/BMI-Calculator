package com.danielsilva.imcApplication.exception;

public class KafkaPublishException extends RuntimeException {
    
    public KafkaPublishException(String message) {
        super(message);
    }
    
    public KafkaPublishException(String message, Throwable cause) {
        super(message, cause);
    }
}
