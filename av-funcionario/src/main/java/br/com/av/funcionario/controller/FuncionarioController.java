package br.com.av.funcionario.controller;

import br.com.av.funcionario.facade.FuncionarioFacade;
import br.com.av.funcionario.model.FuncionarioEntrada;
import br.com.av.funcionario.model.FuncionarioSaida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/funcionario", produces = "application/json")
@Configuration
@CrossOrigin
public class FuncionarioController {

    private FuncionarioFacade funcionarioFacade;

    @Autowired
    public FuncionarioController(FuncionarioFacade funcionarioFacade){
        this.funcionarioFacade = funcionarioFacade;
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody @Valid FuncionarioEntrada funcionarioEntrada) {
            return new ResponseEntity(funcionarioFacade.salvar(funcionarioEntrada), HttpStatus.CREATED);
        }

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(funcionarioFacade.listar());
    }

    @GetMapping("/busca/{id}")
    public ResponseEntity<?> buscaFuncionarioId(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(funcionarioFacade.buscaId(id));
    }

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<?> listarFuncionariosEmpresa(@PathVariable(value = "empresaId") Long empresaId) {
        return ResponseEntity.ok(funcionarioFacade.listarFuncionarioEmpresa(empresaId));
    }

    @PostMapping("/cargo/{id}")
    public ResponseEntity<FuncionarioSaida> atualizaCargo(@PathVariable(value = "id") Long id, @RequestBody FuncionarioEntrada funcionarioEntrada) throws Exception {
        return new ResponseEntity(funcionarioFacade.atualiza(id, funcionarioEntrada), HttpStatus.OK);
    }


}