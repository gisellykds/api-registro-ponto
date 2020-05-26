package br.com.av.gateway.empresa.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaSaida {
    private Long id;
    private String nome;
    private String localidade;
    private String ramoAtividade;
}
