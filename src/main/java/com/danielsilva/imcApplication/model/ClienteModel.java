package com.danielsilva.imcApplication.model;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "IMC")
public class Cliente {

    @Id
    private String id;


    @NotNull
    @Size(min = 3, max = 5)
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
