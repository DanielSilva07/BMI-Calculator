package com.danielsilva.imcApplication.dto;

import lombok.*;

@Getter @Setter
@Data
@NoArgsConstructor
public class ClienteDTO {

    public String id;

    public String nome;

    public Double altura;

    public Double peso ;

    public Double imc;


    /**
     * @return metodo que retorna o valor do imc
     */
    public Double imcCalculator(){
        imc = ( peso / (Math.pow(altura ,2)));
        return imc;
    }


}
