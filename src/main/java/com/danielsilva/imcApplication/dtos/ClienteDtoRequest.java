package com.danielsilva.imcApplication.dtos;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDtoRequest {

                @NotBlank
                String nome;

                @NotNull(message = "Altura é obrigatória")
                BigDecimal altura;

                @NotNull(message = "Peso é obrigatório")
                BigDecimal peso;

                @NotBlank
                @Email
                String email ;
}
