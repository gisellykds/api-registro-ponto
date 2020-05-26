package br.com.av.gateway.controller;

import br.com.av.gateway.bean.EmpresaFuncionario;
import br.com.av.gateway.bean.FuncionarioEmpresa;
import br.com.av.gateway.funcionario.bean.FuncionarioSaida;
import br.com.av.gateway.funcionario.bean.FuncionarioEntrada;
import br.com.av.gateway.service.FuncionarioGatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping(path = "api/gateway/funcionario", produces = "application/json")
@Configuration
@CrossOrigin
@RestController
public class FuncionarioController {
    private FuncionarioGatewayService funcionarioGatewayService;

    @Autowired
    public FuncionarioController(FuncionarioGatewayService funcionarioGatewayService){
        this.funcionarioGatewayService = funcionarioGatewayService;
    }

    @PostMapping("")
    public FuncionarioEmpresa salvar(@RequestBody FuncionarioEntrada funcionarioEntrada) throws Exception {
        return funcionarioGatewayService.salvar(funcionarioEntrada);
    }

    @GetMapping("")
    public List<FuncionarioSaida> listar(){
        return funcionarioGatewayService.listar();
    }

    @GetMapping("/empresa/{empresaId}")
    public EmpresaFuncionario listarFuncionariosEmpresa(@PathVariable(value = "empresaId") Long empresaId) throws Exception {
        return funcionarioGatewayService.listarEmpresasEFuncionarios(empresaId);
    }

    @PatchMapping("/cargo/{id}")
    public FuncionarioEmpresa atualizarCargo(@PathVariable(value = "id") Long id, @RequestBody FuncionarioEntrada funcionarioEntrada){
        return funcionarioGatewayService.atualizarCargo(id, funcionarioEntrada);
    }

    @GetMapping("/busca/{id}")
    public FuncionarioEmpresa buscaFuncionarioId(@PathVariable(value = "id") Long id){
        return funcionarioGatewayService.buscarFuncionarioId(id);
    }
}
