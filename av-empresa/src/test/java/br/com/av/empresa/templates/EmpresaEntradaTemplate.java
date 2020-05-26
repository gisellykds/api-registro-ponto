package br.com.av.empresa.templates;

import br.com.av.empresa.model.EmpresaEntrada;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class EmpresaEntradaTemplate implements TemplateLoader {

    public static final String EMPRESA_ENTRADA_VALIDO = "entrada valido";
    public static final String EMPRESA_ENTRADA_VALIDO_RAMO = "entrada atualizado";

    @Override
    public void load() {
        Fixture.of(EmpresaEntrada.class)
    .addTemplate(EMPRESA_ENTRADA_VALIDO, new Rule(){{
         add("nome", "nome");
         add("ramoAtividade", "ramo");
         add("localidade", "localidade");
         }})
    .addTemplate(EMPRESA_ENTRADA_VALIDO_RAMO, new Rule(){{
         add("nome", "nome");
         add("ramoAtividade", "ramo new");
         add("localidade", "localidade");
    }});
    }
}
