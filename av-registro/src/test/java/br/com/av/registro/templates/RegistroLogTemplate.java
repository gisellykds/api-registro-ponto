package br.com.av.registro.templates;

import br.com.av.registro.model.RegistroLogHora;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

import java.util.Date;

public class RegistroLogTemplate implements TemplateLoader {
    public static final String REGISTRO_LOG_VALIDO = "log valido";
    @Override
    public void load() {
        Fixture.of(RegistroLogHora.class)
                .addTemplate(REGISTRO_LOG_VALIDO, new Rule(){{
                    add("data", new Date());
                    add("horas", "1:0");
                    add("funcionarioId", 1L);
                    add("empresaId", 1L);
                }});
    }
}
