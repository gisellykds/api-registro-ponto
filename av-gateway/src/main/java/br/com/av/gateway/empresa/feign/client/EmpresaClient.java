package br.com.av.gateway.empresa.feign.client;

import br.com.av.gateway.empresa.bean.EmpresaSaida;
import br.com.av.gateway.empresa.bean.EmpresaEntrada;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name="empresa", url="http://localhost:8082/api/empresa")
public interface EmpresaClient {

    @PostMapping("")
    public EmpresaSaida salvar(@RequestBody EmpresaEntrada empresaEntrada);

    @GetMapping("")
    public List<EmpresaSaida> listar();

    @GetMapping("/busca/{id}")
    public EmpresaSaida buscarId(@PathVariable(value = "id") Long id);

    @PostMapping("/{id}")
    public EmpresaSaida atualizaRamo(@PathVariable(value = "id") Long id, @RequestBody EmpresaEntrada empresaEntrada);
}
