package br.com.av.funcionario.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "funcionario")
public class FuncionarioEntity {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @JsonProperty("id")
    @Id
    private Long id;

    @Column(name = "nome")
    @JsonProperty("nome")
    @NotBlank(message = "{nome.not.blank}")
    private String nome;

    @Column(name = "cargo")
    @JsonProperty("cargo")
    @NotBlank(message = "{cargo.not.blank}")
    private String cargo;

    @Column(name = "cpf", nullable = false)
    @JsonProperty("cpf")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")
    private String cpf;

    @Column(name = "empresa_id")
    @JsonProperty("empresa_id")
    private Long empresaId;
}
