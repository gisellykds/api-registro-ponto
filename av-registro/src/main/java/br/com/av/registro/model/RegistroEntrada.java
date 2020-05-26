package br.com.av.registro.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistroEntrada {
    private Integer minutos;
    private Date data;
    private String tarefa;
}
