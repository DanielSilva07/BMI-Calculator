package com.danielsilva.imcApplication.dtos;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ClienteDtoRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void shouldCreateValidClienteDtoRequest() {
        ClienteDtoRequest dto = new ClienteDtoRequest(
                "João Silva",
                new BigDecimal("1.75"),
                new BigDecimal("70.5"),
                "joao.silva@example.com"
        );

        Set<ConstraintViolation<ClienteDtoRequest>> violations = validator.validate(dto);
        assertThat(violations).isEmpty();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    void shouldNotValidateWhenNomeIsBlank(String nome) {
        ClienteDtoRequest dto = new ClienteDtoRequest(
                nome,
                new BigDecimal("1.75"),
                new BigDecimal("70.5"),
                "joao.silva@example.com"
        );

        Set<ConstraintViolation<ClienteDtoRequest>> violations = validator.validate(dto);
        assertThat(violations)
                .hasSize(1)
                .extracting("message")
                .anyMatch(msg -> ((String) msg).contains("não deve estar em branco"));
    }

    @Test
    void shouldNotValidateWhenAlturaIsNull() {
        ClienteDtoRequest dto = new ClienteDtoRequest(
                "João Silva",
                null,
                new BigDecimal("70.5"),
                "joao.silva@example.com"
        );

        Set<ConstraintViolation<ClienteDtoRequest>> violations = validator.validate(dto);
        assertThat(violations)
                .hasSize(1)
                .extracting("message")
                .anyMatch(msg -> msg.equals("Altura é obrigatória"));
    }

    @Test
    void shouldNotValidateWhenPesoIsNull() {
        ClienteDtoRequest dto = new ClienteDtoRequest(
                "João Silva",
                new BigDecimal("1.75"),
                null,
                "joao.silva@example.com"
        );

        Set<ConstraintViolation<ClienteDtoRequest>> violations = validator.validate(dto);
        assertThat(violations)
                .hasSize(1)
                .extracting("message")
                .anyMatch(msg -> msg.equals("Peso é obrigatório"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    void shouldNotValidateWhenEmailIsBlank(String email) {
        ClienteDtoRequest dto = new ClienteDtoRequest(
                "João Silva",
                new BigDecimal("1.75"),
                new BigDecimal("70.5"),
                email
        );

        Set<ConstraintViolation<ClienteDtoRequest>> violations = validator.validate(dto);
        assertThat(violations)
                .extracting("message")
                .anyMatch(msg -> ((String) msg).contains("não deve estar em branco"));
    }

    @Test
    void shouldNotValidateWhenEmailIsInvalid() {
        ClienteDtoRequest dto = new ClienteDtoRequest(
                "João Silva",
                new BigDecimal("1.75"),
                new BigDecimal("70.5"),
                "email-invalido"
        );

        Set<ConstraintViolation<ClienteDtoRequest>> violations = validator.validate(dto);
        assertThat(violations)
                .hasSize(1)
                .extracting("message")
                .anyMatch(msg -> ((String) msg).contains("deve ser um endereço de e-mail bem formado"));
    }
}
