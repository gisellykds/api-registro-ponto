package br.com.av.gateway.funcionario.feign.client;

import br.com.av.gateway.funcionario.bean.FuncionarioSaida;
import br.com.av.gateway.funcionario.bean.FuncionarioEntrada;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name="funcionario", url="http://localhost:8081/api/funcionario")
public interface FuncionarioClient {

    @PostMapping("")
    public FuncionarioSaida salvar(@RequestBody FuncionarioEntrada funcionarioEntrada);

    @GetMapping
    public List<FuncionarioSaida> listar();

    @GetMapping("/busca/{id}")
    public FuncionarioSaida buscaFuncionarioId(@PathVariable(value = "id") Long id);

    @GetMapping("/empresa/{empresaId}")
    public List<FuncionarioSaida> listarFuncionariosEmpresa(@PathVariable(value = "empresaId") Long empresaId);

    @PostMapping("/cargo/{id}")
    public FuncionarioSaida atualizaCargo(@PathVariable(value = "id") Long id, @RequestBody FuncionarioEntrada funcionarioEntrada);

}
