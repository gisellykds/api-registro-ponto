package br.com.av.funcionario.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class FuncionarioSaida {
    private Long id;
    private String nome;
    private String cargo;
    private String cpf;
    private Long empresaId;

}
