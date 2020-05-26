package br.com.av.registro.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistroSaida {
    private Long id;
    private Integer minutos;
    private String tarefa;
    private Long funcionarioId;
    private Long empresaId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone="GMT-3")
    private Date data;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone="GMT-3")
    private Date lastUpdate;
}
