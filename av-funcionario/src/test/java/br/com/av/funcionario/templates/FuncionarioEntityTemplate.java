package br.com.av.funcionario.templates;

import br.com.av.funcionario.model.FuncionarioEntity;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class FuncionarioEntityTemplate implements TemplateLoader {
    public static final String FUNCIONARIO_ENTITY_VALIDO = "entity valido";
    public static final String FUNCIONARIO_ENTITY_INVALIDO = "entity invalido";
    public static final String  FUNCIONARIO_ENTITY_VALIDO_CARGO = "entity cargo";
    @Override
    public void load() {
        Fixture.of(FuncionarioEntity.class)
            .addTemplate(FUNCIONARIO_ENTITY_VALIDO, new Rule(){{
            add("id", 1L);
            add("nome", "nome");
            add("cargo", "cargo");
            add("empresaId", 1L);
            add("cpf", "111.111.111-11");
        }})
        .addTemplate(FUNCIONARIO_ENTITY_VALIDO_CARGO, new Rule(){{
            add("id", 1L);
            add("nome", "nome");
            add("cargo", "novo cargo");
            add("empresaId", 1L);
            add("cpf", "111.111.111-11");
        }})
            .addTemplate(FUNCIONARIO_ENTITY_INVALIDO, new Rule(){{
            add("id", 1L);
            add("nome", "nome");
            add("cargo", null); //cargo invalido
            add("empresaId", 1L);
            add("cpf", "111.111.111-11");
        }});
    }
}
