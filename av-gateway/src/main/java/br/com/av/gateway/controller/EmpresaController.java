package br.com.av.gateway.controller;

import br.com.av.gateway.empresa.bean.EmpresaSaida;
import br.com.av.gateway.empresa.bean.EmpresaEntrada;
import br.com.av.gateway.service.EmpresaGatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = "api/gateway/empresa", produces = "application/json")
@Configuration
@CrossOrigin
@RestController
public class EmpresaController {

    private EmpresaGatewayService empresaServiceGateway;

    @Autowired
    public EmpresaController(EmpresaGatewayService empresaServiceGateway){
        this.empresaServiceGateway = empresaServiceGateway;
    }

    @PostMapping("")
    public EmpresaSaida salvarEmpresa(@RequestBody EmpresaEntrada empresaEntrada){
        return empresaServiceGateway.salvar(empresaEntrada);
    }

    @GetMapping("")
    public List<EmpresaSaida> listar(){
            return empresaServiceGateway.listar();
    }

    @GetMapping("/busca/{id}")
    public EmpresaSaida buscarId(@PathVariable(value = "id") Long id){
        return empresaServiceGateway.buscarId(id);
    }

    @PatchMapping("/{id}")
    public EmpresaSaida atualizarRamo(@PathVariable(value = "id") Long id, @RequestBody EmpresaEntrada empresaEntrada) {
        return empresaServiceGateway.atualizarRamo(id, empresaEntrada);
    }
}
