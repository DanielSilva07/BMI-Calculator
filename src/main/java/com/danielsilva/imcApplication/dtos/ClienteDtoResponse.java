package com.danielsilva.imcApplication.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ClienteDtoResponse implements Serializable {

    private Long id;
    private String nome;
    private String email;
    private BigDecimal altura;
    private BigDecimal peso;
    private BigDecimal imc;
}
