package com.danielsilva.imcApplication.domain;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.io.Serial;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;



@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "imc")
public class ClienteModel implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotNull(message = "Altura é obrigatória")
    @DecimalMin(value = "0.1", message = "Altura deve ser maior que 0")
    private BigDecimal altura;

    @NotNull(message = "Peso é obrigatório")
    @DecimalMin(value = "0.1", message = "Peso deve ser maior que 0")
    private BigDecimal peso;

    private BigDecimal imc;

    @Column(name = "data_de_criacao")
    private LocalDateTime dataDeCriacao;


    /**
         * The BMI is calculated using the formula: weight (kg) / height² (m²)
         * @throws ArithmeticException if height is zero (which would cause division by zero)
         */
        public void imcCalculator() {
        imc = peso.divide(altura.multiply(altura), 2, RoundingMode.HALF_UP);
    }

    @Override
    public String toString() {
        return "ClienteModel{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                ", altura=" + altura +
                ", peso=" + peso +
                ", dataDeCriacao=" + dataDeCriacao +
                ", imc=" + imc +
                '}';
    }
}
