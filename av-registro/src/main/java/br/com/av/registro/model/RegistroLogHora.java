package br.com.av.registro.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "registro_log")
@Getter
@Setter
public class RegistroLogHora {
    private Date data;
    private String horas;
    private Long empresaId;
    private Long funcionarioId;
}
