package br.com.av.empresa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmpresaSaida {
    private Long id;
    private String nome;
    private String localidade;
    private String ramoAtividade;
}
