package br.com.av.registro.controller;

import br.com.av.registro.facade.RegistroFacade;
import br.com.av.registro.model.RegistroEntrada;
import br.com.av.registro.model.RegistroLogHora;
import br.com.av.registro.model.RegistroSaida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;

@RestController
@RequestMapping(path = "api/registro", produces = "application/json")
@Configuration
@CrossOrigin
public class RegistroController {

    private RegistroFacade registroFacade;

    @Autowired
    public RegistroController(RegistroFacade registroFacade){
        this.registroFacade = registroFacade;
    }

    @PostMapping("/{empresaId}/{funcionarioId}")
    public ResponseEntity<RegistroSaida> salvar(@RequestBody RegistroEntrada registroEntrada,
                                                @PathVariable(value = "empresaId") Long empresaId,
                                                @PathVariable(value = "funcionarioId") Long funcionarioId){
        return new ResponseEntity(registroFacade.salvar(registroEntrada, empresaId, funcionarioId), HttpStatus.CREATED);
    }

    @GetMapping("/{funcionarioId}")
    public ResponseEntity<?> buscaRegistroFuncionarioData(@PathVariable(value = "funcionarioId") Long funcionarioId,
                                                          @RequestParam(value = "data")
                                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern="yyyy-MM-dd") Date data) {
        return ResponseEntity.ok(registroFacade.listaFuncionarioIdEData(funcionarioId, data));
    }

    @GetMapping("/total-hora-periodo/{funcionarioId}")
    public ResponseEntity<RegistroLogHora> buscaTotalHoraPeriodo(@PathVariable(value = "funcionarioId") Long funcionarioId,
                                                                 @RequestParam(value = "data")
                                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern="yyyy-MM-dd") Date data) {
        return ResponseEntity.ok(registroFacade.gerarLogHoraData(funcionarioId, data));
    }
}
