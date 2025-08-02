package com.danielsilva.imcApplication.fixtures;
import com.danielsilva.imcApplication.dtos.ClienteDtoRequest;
import com.danielsilva.imcApplication.domain.ClienteModel;

import java.math.BigDecimal;

public class Fixtures {

    public static ClienteDtoRequest buildClienteDtoRequest(){
        return new ClienteDtoRequest(
                "João Silva",
                new BigDecimal("1.78"),
                new BigDecimal("70.0"),
                "joao.silva@example.com"
        );
    }

    public static ClienteModel buildClienteDtoResponse(){
        return ClienteModel.builder()
                .id(1L)
                .email("joao.silva@example.com")
                .nome("João Silva")
                .altura(new BigDecimal("1.78"))
                .peso(new BigDecimal("70.0"))
                .imc(new BigDecimal("22.857142857142858"))
                .build();
    }

    public static ClienteDtoRequest buildClienteDtoRequestIsBlank(){
        return new ClienteDtoRequest(
                " ",
                new BigDecimal("1.78"),
                new BigDecimal("70.0"),
                "joao.silva@example.com"
        );
    }


}
