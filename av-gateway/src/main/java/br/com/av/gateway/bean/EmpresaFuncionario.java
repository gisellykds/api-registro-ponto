package br.com.av.gateway.bean;

import br.com.av.gateway.empresa.bean.EmpresaSaida;
import br.com.av.gateway.funcionario.bean.FuncionarioSaida;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class EmpresaFuncionario {
    EmpresaSaida empresa;
    List<FuncionarioSaida> funcionarios;
}