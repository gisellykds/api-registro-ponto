package br.com.av.gateway.registro.service;

import br.com.av.gateway.registro.bean.RegistroSaida;
import br.com.av.gateway.registro.bean.RegistroEntrada;
import br.com.av.gateway.registro.bean.RegistroLogHoraBean;
import br.com.av.gateway.registro.feign.client.RegistroClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RegistroService {

    private RegistroClient registroClient;

    @Autowired
    public RegistroService(RegistroClient registroClient){
        this.registroClient = registroClient;
    }

    public RegistroSaida salvar(RegistroEntrada registroEntrada, Long empresaId, Long funcionarioId){
        return registroClient.salvar(registroEntrada, empresaId, funcionarioId);
    }

    public List<RegistroSaida> buscaRegistroFuncionarioData(Long funcionarioId, Date data){
        return registroClient.buscaRegistroFuncionarioData(funcionarioId, data);
    }

    public RegistroLogHoraBean buscaTotalHoraPeriodo(Long funcionarioId, Date data){
        return registroClient.buscaTotalHoraPeriodo(funcionarioId, data);
    }
}
