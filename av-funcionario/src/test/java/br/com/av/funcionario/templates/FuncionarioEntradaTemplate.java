package br.com.av.funcionario.templates;

import br.com.av.funcionario.model.FuncionarioEntrada;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class FuncionarioEntradaTemplate implements TemplateLoader {
    public static final String FUNCIONARIO_ENTRADA_VALIDO = "entrada valido";
    public static final String FUNCIONARIO_ENTRADA_INVALIDO = "entrada invalido";
    public static final String FUNCIONARIO_ENTRADA_VALIDO_CARGO = "entrada valido cargo";

        @Override
        public void load() {
            Fixture.of(FuncionarioEntrada.class)
                .addTemplate(FUNCIONARIO_ENTRADA_VALIDO, new Rule(){{
                   add("nome", "nome");
                   add("cargo", "cargo");
                   add("empresaId", 1L);
                   add("cpf", "111.111.111-11");
                }})
                .addTemplate(FUNCIONARIO_ENTRADA_INVALIDO, new Rule(){{
                    add("nome", "nome");
                    add("cargo", null); //cargo invalido
                    add("empresaId", 1L);
                    add("cpf", "111.111.111-11");
                }})
                .addTemplate(FUNCIONARIO_ENTRADA_VALIDO_CARGO, new Rule(){{
                    add("cargo", "novo cargo"); //cargo novo
                }});
        }
}
