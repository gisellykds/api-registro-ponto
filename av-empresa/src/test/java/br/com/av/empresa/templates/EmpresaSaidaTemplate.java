package br.com.av.empresa.templates;

import br.com.av.empresa.model.EmpresaSaida;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class EmpresaSaidaTemplate implements TemplateLoader {

    public static final String EMPRESA_SAIDA_VALIDO = "saida valido";
    public static final String EMPRESA_SAIDA_RAMO = "saida atualizado";

    @Override
    public void load() {
        Fixture.of(EmpresaSaida.class)
                .addTemplate(EMPRESA_SAIDA_VALIDO, new Rule(){{
                    add("id", 1L);
                    add("nome", "nome");
                    add("ramoAtividade", "ramo");
                    add("localidade", "localidade");
                }})
                .addTemplate(EMPRESA_SAIDA_RAMO, new Rule(){{
                    add("id", 1L);
                    add("nome", "nome");
                    add("ramoAtividade", "ramo new");
                    add("localidade", "localidade");
                }});
    }
}
