package br.com.av.empresa.facade;

import br.com.av.empresa.mapper.EmpresaMapper;
import br.com.av.empresa.model.EmpresaEntity;
import br.com.av.empresa.model.EmpresaEntrada;
import br.com.av.empresa.model.EmpresaSaida;
import br.com.av.empresa.repository.EmpresaRepository;
import br.com.av.empresa.utils.exceptions.ObjInexistenteException;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmpresaFacade {

    private EmpresaRepository empresaRepository;
    private EmpresaMapper mapper = Mappers.getMapper(EmpresaMapper.class);

    @Autowired
    public EmpresaFacade(EmpresaRepository empresaRepository){
        this.empresaRepository = empresaRepository;
    }

    public EmpresaSaida salvar(EmpresaEntrada empresaEntrada) {
        EmpresaEntity empresaEntity = mapper.mapToEntity(empresaEntrada);
        empresaEntity = empresaRepository.save(empresaEntity);
        EmpresaSaida saida = mapper.mapToSaida(empresaEntity);
        return saida;
    }

    public List<EmpresaSaida> listar() {
        List<EmpresaEntity> empresaEntities = empresaRepository.findAll();
        List<EmpresaSaida> saida = new ArrayList<>();
        if (empresaEntities.isEmpty())
            throw new ObjInexistenteException("Nenhuma empresa cadastrada.");
        for (EmpresaEntity emp : empresaEntities){
            saida.add(mapper.mapToSaida(emp));
        }
        return saida;
    }

    public EmpresaSaida atualiza(Long id, EmpresaEntrada empresaEntrada) {
        Optional<EmpresaEntity> empresaEntityOptional = empresaRepository.findById(id);
        if (!empresaEntityOptional.isPresent())
            throw new ObjInexistenteException("empresa não encontrada.");
        EmpresaEntity entity = empresaEntityOptional.get();
        entity.setRamoAtividade(empresaEntrada.getRamoAtividade());
        entity = empresaRepository.save(entity);
        EmpresaSaida saida = mapper.mapToSaida(entity);
        return saida;
    }

    public EmpresaSaida buscaId(Long id) {
        Optional<EmpresaEntity> empresaEntityOpt = empresaRepository.findById(id);
        if (!empresaEntityOpt.isPresent())
            throw new ObjInexistenteException("Empresa com id: " + id + " não encontrada.");
        EmpresaEntity empresaEntity = empresaEntityOpt.get();
        EmpresaSaida saida = mapper.mapToSaida(empresaEntity);
        return saida;
    }
}
