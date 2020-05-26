package br.com.av.funcionario.mapper;

import br.com.av.funcionario.model.*;
import org.mapstruct.Mapper;

@Mapper
public interface FuncionarioMapper {

    FuncionarioEntity mapToEntity(FuncionarioEntrada funcionarioEntrada);

    FuncionarioSaida mapToSaida(FuncionarioEntity funcionarioEntity);
}
