package com.danielsilva.imcApplication.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDtoRequest {
                String nome;
                Double altura;
                Double peso ;
                String email ;

}

