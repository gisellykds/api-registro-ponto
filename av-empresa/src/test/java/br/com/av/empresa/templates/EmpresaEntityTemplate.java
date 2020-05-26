package br.com.av.empresa.templates;

import br.com.av.empresa.model.EmpresaEntity;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class EmpresaEntityTemplate implements TemplateLoader {

    public static final String EMPRESA_ENTITY_VALIDO = "entity valido";
    public static final String EMPRESA_ENTITY_VALIDO_RAMO = "entity atualizado";

    @Override
    public void load() {
        Fixture.of(EmpresaEntity.class)
             .addTemplate(EMPRESA_ENTITY_VALIDO, new Rule(){{
                 add("id", 1L);
                 add("nome", "nome");
                 add("ramoAtividade", "ramo");
                 add("localidade", "localidade");
             }})
             .addTemplate(EMPRESA_ENTITY_VALIDO_RAMO, new Rule(){{
                add("id", 1L);
                add("nome", "nome");
                add("ramoAtividade", "ramo new");
                add("localidade", "localidade");
        }});
    }
}
