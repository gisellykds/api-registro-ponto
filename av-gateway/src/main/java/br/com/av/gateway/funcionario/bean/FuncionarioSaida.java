package br.com.av.gateway.funcionario.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FuncionarioSaida {
    private Long id;
    private String nome;
    private String cargo;
    private String cpf;
    private Long empresaId;
}
