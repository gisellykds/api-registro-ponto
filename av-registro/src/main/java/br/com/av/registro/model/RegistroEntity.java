package br.com.av.registro.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "registro")
public class RegistroEntity {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @JsonProperty("id")
    @Id
    private Long id;

    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializers.DateDeserializer.class)
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone="GMT-3")
    @Column(name = "last_update")
    private Date lastUpdate;

    @Column(name = "minutos")
    private Integer minutos;

    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializers.DateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-mm-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "data")
    private Date data;

    @Column(name = "tarefa")
    @JsonProperty("tarefa")
    private String tarefa;

    @Column(name = "funcionario_id")
    @JsonProperty("funcionario_id")
    private Long funcionarioId;

    @Column(name = "empresaId")
    @JsonProperty("empresaId")
    private Long empresaId;

}