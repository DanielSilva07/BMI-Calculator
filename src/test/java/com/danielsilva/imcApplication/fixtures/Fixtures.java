package com.danielsilva.imcApplication.fixtures;

import com.danielsilva.imcApplication.dtos.ClienteDtoRequest;
import com.danielsilva.imcApplication.domain.ClienteModel;

public class Fixtures {

    public static ClienteDtoRequest buildClienteDtoRequest(){
        return new ClienteDtoRequest(
                "João Silva",
                1.75,
                70.0,
                "joao.silva@example.com"
        );
    }

    public static ClienteModel buildClienteDtoResponse(){
        return new ClienteModel(
                1L,
                "test",
                "joao.silva@example.com",
                "João Silva",
                1.75,
                70.0,
                22.857142857142858
        );
    }


}
