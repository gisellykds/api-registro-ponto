package br.com.av.registro.mapper;

import br.com.av.registro.model.RegistroEntity;
import br.com.av.registro.model.RegistroEntrada;
import br.com.av.registro.model.RegistroLogHora;
import br.com.av.registro.model.RegistroSaida;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface RegistroMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "lastUpdate", ignore = true),
            @Mapping(target = "funcionarioId", ignore = true),
            @Mapping(target = "empresaId", ignore = true)
    })
    RegistroEntity mapToEntity(RegistroEntrada registroEntrada);

    RegistroSaida mapToSaida(RegistroEntity registroEntity);

    @Mapping(target = "horas", ignore = true)
    RegistroLogHora mapToLog(RegistroEntity registroEntity);
}
