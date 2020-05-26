package br.com.av.empresa.mapper;

import br.com.av.empresa.model.EmpresaEntity;
import br.com.av.empresa.model.EmpresaEntrada;
import br.com.av.empresa.model.EmpresaSaida;
import org.mapstruct.Mapper;

@Mapper
public interface EmpresaMapper {

    EmpresaEntity mapToEntity(EmpresaEntrada empresaEntrada);

    EmpresaSaida mapToSaida(EmpresaEntity empresaEntity);
}
