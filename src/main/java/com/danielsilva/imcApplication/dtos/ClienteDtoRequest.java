package com.danielsilva.imcApplication.dtos;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDtoRequest {

                String nome;
                BigDecimal altura;
                BigDecimal peso ;
                String email ;
}

