package com.danielsilva.imcApplication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "IMC")
public class Cliente {

    private String id;

    private String nome;

    private Double altura;

    private Double peso ;

    private Double imc;


    /**
     * @return metodo que retorna o valor do imc
     */
        public Double imcCalculator(){
            imc = ( peso / (altura * altura));
            return imc;

        }


}
