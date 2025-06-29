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
        return new ClienteModel(
                1L,
                "test",
                "joao.silva@example.com",
                "João Silva",
                new BigDecimal("1.78"),
                new BigDecimal("70.0"),
                new BigDecimal("22.857142857142858")
        );
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
