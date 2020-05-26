package br.com.av.gateway.registro.feign.client;

import br.com.av.gateway.registro.bean.RegistroSaida;
import br.com.av.gateway.registro.bean.RegistroEntrada;
import br.com.av.gateway.registro.bean.RegistroLogHoraBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@FeignClient(name="registro", url="http://localhost:8083/api/registro")
public interface RegistroClient {

    @PostMapping("/{empresaId}/{funcionarioId}")
    public RegistroSaida salvar(@RequestBody RegistroEntrada registroEntrada,
                                @PathVariable(value = "empresaId") Long empresaId,
                                @PathVariable(value = "funcionarioId") Long funcionarioId);

    @GetMapping("/{funcionarioId}")
    public List<RegistroSaida> buscaRegistroFuncionarioData(@PathVariable(value = "funcionarioId") Long funcionarioId,
                                                            @RequestParam(value = "data")
                                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern="yyyy-MM-dd") Date data);

    @GetMapping("/total-hora-periodo/{funcionarioId}")
    public RegistroLogHoraBean buscaTotalHoraPeriodo(@PathVariable(value = "funcionarioId") Long funcionarioId,
                                                                 @RequestParam(value = "data")
                                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern="yyyy-MM-dd") Date data);

}
