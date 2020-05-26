package br.com.av.empresa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "empresa")
public class EmpresaEntity {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @JsonProperty("id")
    @Id
    private Long id;

    @Column(name = "nome")
    @JsonProperty("nome")
    @NotBlank(message = "{nome.not.blank}")
    private String nome;

    @Column(name = "localidade")
    @JsonProperty("localidade")
    @NotBlank(message = "{localidade.not.blank}")
    private String localidade;

    @Column(name = "ramo_atividade")
    @JsonProperty("ramo_atividade")
    @NotBlank(message = "{ramo.not.blank}")
    private String ramoAtividade;
}
