package com.danielsilva.imcApplication.dtos;

public record EmailModel(
        String id,
        String emailFrom,
        String emailTo,
        String subject,
        String texto) {
}



