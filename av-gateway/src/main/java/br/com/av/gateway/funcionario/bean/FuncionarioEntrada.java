package br.com.av.gateway.funcionario.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FuncionarioEntrada {
    private String nome;
    private String cargo;
    private String cpf;
    private Long empresaId;
}
