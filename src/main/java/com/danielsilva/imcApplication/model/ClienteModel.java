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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getImc() {
        return imc;
    }

    public void setImc(Double imc) {
        this.imc = imc;
    }
}
