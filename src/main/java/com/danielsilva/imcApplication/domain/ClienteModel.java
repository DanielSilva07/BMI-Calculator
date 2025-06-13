package com.danielsilva.imcApplication.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.math.BigDecimal;
import java.math.RoundingMode;


@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Imc")
public class ClienteModel implements java.io.Serializable {
    @Serial
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
         * Calculates the Body Mass Index (BMI) for the client.
         * <p>
         * The BMI is calculated using the formula: weight (kg) / height² (m²)
         *
         * @throws ArithmeticException if height is zero (which would cause division by zero)
         */
        public void imcCalculator() {
            imc = peso.divide(altura.multiply(altura), 2, RoundingMode.HALF_UP);
        }


}
