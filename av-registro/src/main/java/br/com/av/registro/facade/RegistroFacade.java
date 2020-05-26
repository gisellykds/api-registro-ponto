package br.com.av.registro.facade;

import br.com.av.registro.mapper.RegistroMapper;
import br.com.av.registro.model.RegistroEntity;
import br.com.av.registro.model.RegistroEntrada;
import br.com.av.registro.model.RegistroLogHora;
import br.com.av.registro.model.RegistroSaida;
import br.com.av.registro.repository.RegistroRepository;
import br.com.av.registro.utils.exception.ObjInexistenteException;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RegistroFacade {

    private RegistroRepository registroRepository;
    private RegistroMapper mapper = Mappers.getMapper(RegistroMapper.class);

    @Autowired
    public RegistroFacade(RegistroRepository registroRepository){
        this.registroRepository = registroRepository;
    }


    public RegistroSaida salvar(RegistroEntrada registroEntrada,Long empresaId, Long funcionarioId){
        RegistroEntity registroEntity = mapper.mapToEntity(registroEntrada);
        registroEntity.setEmpresaId(empresaId);
        registroEntity.setFuncionarioId(funcionarioId);
        registroEntity = registroRepository.save(registroEntity);
        RegistroSaida saida = mapper.mapToSaida(registroEntity);
        return saida;
    }

    public List<RegistroSaida> listaFuncionarioIdEData(Long funcionarioId, Date data) {
        List<RegistroEntity> registroEntities = registroRepository.findByDataAndFuncionarioId(data, funcionarioId);
        List<RegistroSaida> saida = new ArrayList<>();
        if (registroEntities.isEmpty())
            throw new ObjInexistenteException("Nenhum registro encontrado em data:" + data + " para funcionario com id: " + funcionarioId);
        for (RegistroEntity reg : registroEntities){
            saida.add(mapper.mapToSaida(reg));
        }
        return saida;
    }

    public RegistroLogHora gerarLogHoraData(Long funcionarioId, Date data) {
        List<RegistroEntity> registroEntities = registroRepository.findByDataAndFuncionarioId(data, funcionarioId);
        RegistroLogHora registroLogHora = new RegistroLogHora();
        Integer horas = 0;
        if (registroEntities.isEmpty())
            throw new ObjInexistenteException("Nenhum registro encontrado em data: " + data + " para funcionario com id: " + funcionarioId);
        for (RegistroEntity reg : registroEntities){
                horas += reg.getMinutos();
        }
        registroLogHora = mapper.mapToLog(registroEntities.get(0));
        registroLogHora.setHoras(converterMinHora(horas));
        return registroLogHora;
    }

    public String converterMinHora(Integer horas){
        Integer min = horas % 60;
        Integer hr = (horas - min) / 60;
        String horaFinal = hr +":"+min;
        return horaFinal;
    }
}