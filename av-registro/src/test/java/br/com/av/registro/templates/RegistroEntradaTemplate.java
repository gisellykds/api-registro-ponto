package br.com.av.registro.templates;

import br.com.av.registro.model.RegistroEntity;
import br.com.av.registro.model.RegistroEntrada;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

import java.util.Date;

public class RegistroEntradaTemplate implements TemplateLoader {
    public static final String REGISTRO_ENTRADA_VALIDO = "entrada valido";
    @Override
    public void load() {
        Fixture.of(RegistroEntrada.class)
            .addTemplate(REGISTRO_ENTRADA_VALIDO, new Rule(){{
            add("minutos", 60);
            add("data", new Date());
            add("tarefa", "tarefa");
        }});
    }
}
