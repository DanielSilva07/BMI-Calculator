package com.danielsilva.imcApplication.model;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "IMC")
public class ClienteModel  {

    @Id
    private String id;

    @NotNull
    private String nome;

    private Double altura;

    private Double peso ;

    private Double imc;


    /**
     * @return metodo que retorna o valor do imc
     */
        public Double imcCalculator(){
            imc = ( peso / (Math.pow(altura ,2)));
            return imc;
        }

}
