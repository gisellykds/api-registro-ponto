package br.com.av.empresa.controller;

import br.com.av.empresa.facade.EmpresaFacade;
import br.com.av.empresa.model.EmpresaEntrada;
import br.com.av.empresa.model.EmpresaSaida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/empresa", produces = "application/json")
@Configuration
@CrossOrigin
public class EmpresaController {

    private EmpresaFacade empresaFacade;

    @Autowired
    public EmpresaController(EmpresaFacade empresaFacade){
        this.empresaFacade = empresaFacade;
    }

    @PostMapping
    public ResponseEntity<EmpresaSaida> salvar(@RequestBody @Valid EmpresaEntrada empresaEntrada){
        return new ResponseEntity(empresaFacade.salvar(empresaEntrada), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(empresaFacade.listar());
    }

    @GetMapping("/busca/{id}")
    public ResponseEntity<EmpresaSaida> buscarId(@PathVariable(value = "id") Long id){
        return ResponseEntity.ok(empresaFacade.buscaId(id));
    }

    @PostMapping("/{id}")
    public ResponseEntity<EmpresaSaida> atualizaRamo(@PathVariable(value = "id") Long id, @RequestBody EmpresaEntrada empresaEntrada) {
        return ResponseEntity.ok(empresaFacade.atualiza(id, empresaEntrada));
    }

}
