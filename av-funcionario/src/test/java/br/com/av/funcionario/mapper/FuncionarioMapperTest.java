package br.com.av.funcionario.mapper;
import br.com.av.funcionario.facade.FuncionarioFacade;
import br.com.av.funcionario.model.FuncionarioEntity;
import br.com.av.funcionario.model.FuncionarioEntrada;
import br.com.av.funcionario.model.FuncionarioSaida;
import br.com.av.funcionario.templates.FuncionarioEntityTemplate;
import br.com.av.funcionario.templates.FuncionarioEntradaTemplate;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class FuncionarioMapperTest {

    @Spy
    private FuncionarioMapper mapper = Mappers.getMapper(FuncionarioMapper.class);

    @InjectMocks
    private FuncionarioFacade funcionarioFacade;

    @Before
    public void init() {
        FixtureFactoryLoader.loadTemplates("br.com.av.funcionario.templates");
    }

    @Test
    public void deveConverterEntradaParaEntity() {
        // GIVEN
        FuncionarioEntrada entrada = Fixture.from(FuncionarioEntrada.class).gimme(FuncionarioEntradaTemplate.FUNCIONARIO_ENTRADA_VALIDO);
        //WHEN
        FuncionarioEntity entity = mapper.mapToEntity(entrada);
        // THEN
        Assert.assertNotNull(entity);
        Assert.assertEquals(entrada.getNome(), entity.getNome());
        Assert.assertEquals(entrada.getCargo(), entity.getCargo());
        Assert.assertEquals(entrada.getCpf(), entity.getCpf());
        Assert.assertEquals(entrada.getEmpresaId(), entity.getEmpresaId());
        verify(mapper, times(1)).mapToEntity(any(FuncionarioEntrada.class));
    }

    @Test
    public void deveConverterEntityParaSaida() {
        //GIVEN
        FuncionarioEntity entity =  Fixture.from(FuncionarioEntity.class).gimme(FuncionarioEntityTemplate.FUNCIONARIO_ENTITY_VALIDO);
        //WHEN
        FuncionarioSaida saida = mapper.mapToSaida(entity);
        //THEN
        Assert.assertNotNull(saida);
        Assert.assertEquals(entity.getId(), saida.getId());
        Assert.assertEquals(entity.getNome(), saida.getNome());
        Assert.assertEquals(entity.getCargo(), saida.getCargo());
        Assert.assertEquals(entity.getEmpresaId(), saida.getEmpresaId());
        Assert.assertEquals(entity.getCpf(), saida.getCpf());
        verify(mapper, times(1)).mapToSaida(any(FuncionarioEntity.class));
    }

    @Test
    public void deveConverterEntradaParaEntityNull() {
        // GIVEN
        FuncionarioEntrada entrada = null;
        //WHEN
        FuncionarioEntity entity = mapper.mapToEntity(entrada);
        // THEN
        Assert.assertNull(entity);
    }

    @Test
    public void deveConverterEntityParaSaidaNull() {
        // GIVEN
        FuncionarioEntity entity = null;
        //WHEN
        FuncionarioSaida saida = mapper.mapToSaida(entity);
        // THEN
        Assert.assertNull(saida);
    }
}
