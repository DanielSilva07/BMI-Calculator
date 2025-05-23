package com.danielsilva.imcApplication.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "IMC")
public class ClienteModel  {

    private String id;
    @JsonIgnore
    private String emailFrom = "example@example.com";
    private String email;
    private String nome;
    private Double altura;
    private Double peso;
    private Double imc;



    /**
     * @return metodo que retorna o valor do imc
     */
        public Double imcCalculator(){
            imc = ( peso / (Math.pow(altura ,2)));
            return imc;
        }


}
