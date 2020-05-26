package br.com.av.funcionario.facade;

import br.com.av.funcionario.mapper.FuncionarioMapper;
import br.com.av.funcionario.model.*;
import br.com.av.funcionario.repository.FuncionarioRepository;
import br.com.av.funcionario.utils.exceptions.ObjInexistenteException;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class FuncionarioFacade {
    private FuncionarioRepository funcionarioRepository;

    private FuncionarioMapper mapper = Mappers.getMapper( FuncionarioMapper.class );

    @Autowired
    public FuncionarioFacade(FuncionarioRepository funcionarioRepository){
        this.funcionarioRepository = funcionarioRepository;
    }

    public FuncionarioSaida salvar(FuncionarioEntrada funcionarioEntrada){
        FuncionarioEntity funcionarioEntity = mapper.mapToEntity(funcionarioEntrada);
        funcionarioEntity = funcionarioRepository.save(funcionarioEntity);
        FuncionarioSaida saida = mapper.mapToSaida(funcionarioEntity);
        return saida;
    }

    public List<FuncionarioSaida> listar() {
        List<FuncionarioEntity> entity = funcionarioRepository.findAll();
        List<FuncionarioSaida> saida = new ArrayList<>();
        if (entity.isEmpty())
            throw new ObjInexistenteException("Nenhum funcionário cadastrado.");
        for (FuncionarioEntity func : entity){
            saida.add(mapper.mapToSaida(func));
        }
        return saida;
    }

    public List<FuncionarioSaida> listarFuncionarioEmpresa(Long empresaId) {
        List<FuncionarioEntity> funcionariosEntity = funcionarioRepository.findByEmpresaId(empresaId);
        List<FuncionarioSaida> saida = new ArrayList<>();
        if (funcionariosEntity.isEmpty())
            throw new ObjInexistenteException("Nenhum funcionário cadastrado em empresa com id: " + empresaId + ".");
        for (FuncionarioEntity func : funcionariosEntity){
            saida.add(mapper.mapToSaida(func));
        }
        return saida;
    }

    public FuncionarioSaida atualiza(Long id, FuncionarioEntrada funcionarioEntrada) {
        Optional<FuncionarioEntity> funcionarioOpt = funcionarioRepository.findById(id);
        if (!funcionarioOpt.isPresent())
            throw new ObjInexistenteException("Funcionario com id: " + id + " não cadastrado.");
        FuncionarioEntity funcionarioEntity = funcionarioOpt.get();
        funcionarioEntity.setCargo(funcionarioEntrada.getCargo());
        funcionarioEntity = funcionarioRepository.save(funcionarioEntity);
        FuncionarioSaida saida = mapper.mapToSaida(funcionarioEntity);
        return saida;
    }

    public FuncionarioSaida buscaId(Long id) {
       Optional<FuncionarioEntity> funcionarioEntityOpt = funcionarioRepository.findById(id);
        if (!funcionarioEntityOpt.isPresent())
            throw new ObjInexistenteException("Nenhum funcionario com id: " + id + " cadastrado.");
        FuncionarioEntity funcionarioEntity = funcionarioEntityOpt.get();
        FuncionarioSaida saida = mapper.mapToSaida(funcionarioEntity);
        return saida;
    }
}