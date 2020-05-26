package br.com.av.gateway.bean;


import br.com.av.gateway.registro.bean.RegistroSaida;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegistroEmpresaFuncionario {
    @JsonProperty("registro")
    RegistroSaida registro;
    @JsonProperty("funcionario_empresa")
    FuncionarioEmpresa empresaFuncionario;
}
