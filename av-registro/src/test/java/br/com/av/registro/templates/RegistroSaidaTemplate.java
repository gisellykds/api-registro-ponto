package br.com.av.registro.templates;

import br.com.av.registro.model.RegistroSaida;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

import java.util.Date;

public class RegistroSaidaTemplate implements TemplateLoader {
    public static final String REGISTRO_SAIDA_VALIDO = "saida valido";
    @Override
    public void load() {
        Fixture.of(RegistroSaida.class)
                .addTemplate(REGISTRO_SAIDA_VALIDO, new Rule(){{
                    add("id", 1L);
                    add("lastUpdate", new Date());
                    add("minutos", 60);
                    add("data", new Date());
                    add("tarefa", "tarefa");
                    add("funcionarioId", 1L);
                    add("empresaId", 1L);
                }});
    }
}
