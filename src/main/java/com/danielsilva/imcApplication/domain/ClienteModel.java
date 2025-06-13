package com.danielsilva.imcApplication.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
 import java.math.BigDecimal;
import java.math.RoundingMode;


@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Imc")
public class ClienteModel implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    private String emailFrom = "example@example.com";
    private String email;
    private String nome;
    private BigDecimal altura;
    private BigDecimal peso;
    private BigDecimal imc;



    /**
     * @return metodo que retorna o valor do imc
     */
        /**
         * Calculates the Body Mass Index (BMI) for the client.
         *
         * The BMI is calculated using the formula: weight (kg) / height² (m²)
         *
         * @return The calculated BMI value as a BigDecimal
         * @throws ArithmeticException if height is zero (which would cause division by zero)
         */
        public void imcCalculator() {
            // Calculate BMI using the formula: weight / height²
            imc = peso.divide(altura.multiply(altura), 2, RoundingMode.HALF_UP);
        }


}
