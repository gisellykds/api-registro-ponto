package br.com.av.gateway.controller;

import br.com.av.gateway.bean.RegistroEmpresaFuncionario;
import br.com.av.gateway.bean.RegistroLogFuncionarioEmpresa;
import br.com.av.gateway.registro.bean.RegistroSaida;
import br.com.av.gateway.registro.bean.RegistroEntrada;
import br.com.av.gateway.service.RegistroGatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

@RequestMapping(path = "api/gateway/registro", produces = "application/json")
@Configuration
@CrossOrigin
@RestController
public class RegistroController {
    private RegistroGatewayService registroGatewayService;

    @Autowired
    public RegistroController(RegistroGatewayService registroGatewayService){
        this.registroGatewayService = registroGatewayService;
    }

    @PostMapping("/{empresaId}/{funcionarioId}")
    public RegistroEmpresaFuncionario salvar(@RequestBody RegistroEntrada registroEntrada,
                                             @PathVariable(value = "empresaId") Long empresaId,
                                             @PathVariable(value = "funcionarioId") Long funcionarioId) {
        return registroGatewayService.salvar(registroEntrada, empresaId, funcionarioId);
    }

    @GetMapping("/{funcionarioId}")
    public List<RegistroSaida> buscaRegistroFuncionarioData(@PathVariable(value = "funcionarioId") Long funcionarioId,
                                                            @RequestParam(value = "data")
                                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern="yyyy-MM-dd") Date data) {
        return registroGatewayService.buscaRegistroFuncIdEData(funcionarioId, data);
    }

    @GetMapping("/horas/{funcionarioId}")
    public RegistroLogFuncionarioEmpresa buscaTotalHoraPeriodo(@PathVariable(value = "funcionarioId") Long funcionarioId,
                                                               @RequestParam(value = "data")
                                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern="yyyy-MM-dd") Date data) {
        return registroGatewayService.buscaTotalHoraPeriodo(funcionarioId, data);
    }


}
