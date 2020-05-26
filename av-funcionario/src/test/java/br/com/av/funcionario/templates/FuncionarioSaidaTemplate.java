package br.com.av.funcionario.templates;

import br.com.av.funcionario.model.FuncionarioSaida;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class FuncionarioSaidaTemplate implements TemplateLoader {
    public static final String FUNCIONARIO_SAIDA_VALIDO = "saida valido";
    public static final String FUNCIONARIO_SAIDA_VALIDO_CARGO= "saida valido cargo";
    @Override
    public void load() {
        Fixture.of(FuncionarioSaida.class)
        .addTemplate(FUNCIONARIO_SAIDA_VALIDO, new Rule(){{
            add("id", 1L);
            add("nome", "nome");
            add("cargo", "cargo");
            add("empresaId", 1L);
            add("cpf", "111.111.111-11");
        }})
        .addTemplate(FUNCIONARIO_SAIDA_VALIDO_CARGO, new Rule(){{
            add("id", 1L);
            add("nome", "nome");
            add("cargo", "novo cargo");
            add("empresaId", 1L);
            add("cpf", "111.111.111-11");
        }});
    }
}
