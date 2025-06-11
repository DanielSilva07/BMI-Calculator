package com.danielsilva.imcApplication.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ClienteModel implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
