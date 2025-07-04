package com.danielsilva.imcApplication.events;

import com.danielsilva.imcApplication.domain.ClienteModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteCriadoEvent implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String nome;
    private String email;
    private BigDecimal imc;

    
    public ClienteCriadoEvent(ClienteModel cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.email = cliente.getEmail();
        this.imc = cliente.getImc() != null ? cliente.getImc() : null;
    }
}
