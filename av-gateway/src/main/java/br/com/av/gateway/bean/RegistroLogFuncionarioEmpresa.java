package br.com.av.gateway.bean;

import br.com.av.gateway.registro.bean.RegistroLogHoraBean;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegistroLogFuncionarioEmpresa {
    @JsonProperty("extrato_horas")
    RegistroLogHoraBean registroLogH;
    @JsonProperty("funcionario_empresa")
    FuncionarioEmpresa funcionarioEmpresa;
}
