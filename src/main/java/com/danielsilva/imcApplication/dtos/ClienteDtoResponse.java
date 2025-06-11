package com.danielsilva.imcApplication.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ClienteDtoResponse {

    private Long id;
    private String nome;
    private String email;
    private Double altura;
    private Double peso;
    private Double imc;
}
