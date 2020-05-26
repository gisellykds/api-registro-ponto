package br.com.av.registro.mapper;

import br.com.av.registro.facade.RegistroFacade;
import br.com.av.registro.model.RegistroEntity;
import br.com.av.registro.model.RegistroEntrada;
import br.com.av.registro.model.RegistroLogHora;
import br.com.av.registro.model.RegistroSaida;
import br.com.av.registro.templates.RegistroEntityTemplate;
import br.com.av.registro.templates.RegistroEntradaTemplate;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class RegistroMapperTest {
    @Spy
    private RegistroMapper mapper = Mappers.getMapper(RegistroMapper.class);

    @InjectMocks
    private RegistroFacade registroFacade;

    @Before
    public void init() {
        FixtureFactoryLoader.loadTemplates("br.com.av.registro.templates");
    }

    @Test
    public void deveConverterEntradaParaEntity() {
        // GIVEN
        RegistroEntrada entrada = Fixture.from(RegistroEntrada.class).gimme(RegistroEntradaTemplate.REGISTRO_ENTRADA_VALIDO);
        //WHEN
        RegistroEntity entity = mapper.mapToEntity(entrada);
        // THEN
        Assert.assertNotNull(entity);
        Assert.assertEquals(entrada.getData(), entity.getData());
        Assert.assertEquals(entrada.getMinutos(), entity.getMinutos());
        Assert.assertEquals(entrada.getTarefa(), entity.getTarefa());
        Assert.assertEquals(entrada.getData(), entity.getData());
        verify(mapper, times(1)).mapToEntity(any(RegistroEntrada.class));
    }

    @Test
    public void deveConverterEntityParaSaida() {
        //GIVEN
        RegistroEntity entity = Fixture.from(RegistroEntity.class).gimme(RegistroEntityTemplate.REGISTRO_ENTITY_VALIDO);
        //WHEN
        RegistroSaida saida = mapper.mapToSaida(entity);
        //THEN
        Assert.assertNotNull(saida);
        Assert.assertEquals(entity.getId(), saida.getId());
        Assert.assertEquals(entity.getData(), saida.getData());
        Assert.assertEquals(entity.getLastUpdate(), saida.getLastUpdate());
        Assert.assertEquals(entity.getMinutos(), saida.getMinutos());
        Assert.assertEquals(entity.getTarefa(), saida.getTarefa());
        Assert.assertEquals(entity.getFuncionarioId(), saida.getFuncionarioId());
        Assert.assertEquals(entity.getEmpresaId(), saida.getEmpresaId());
        verify(mapper, times(1)).mapToSaida(any(RegistroEntity.class));

    }

    @Test
    public void deveConverterEntityParaLog() {
        //GIVEN
        RegistroEntity entity = Fixture.from(RegistroEntity.class).gimme(RegistroEntityTemplate.REGISTRO_ENTITY_VALIDO);
        //WHEN
        RegistroLogHora log = mapper.mapToLog(entity);
        //THEN
        Assert.assertNotNull(log);
        Assert.assertEquals(log.getData(), entity.getData());
        Assert.assertEquals(log.getEmpresaId(), entity.getEmpresaId());
        Assert.assertEquals(log.getFuncionarioId(), entity.getFuncionarioId());
        Assert.assertEquals(log.getHoras(), null);
        verify(mapper, times(1)).mapToLog(any(RegistroEntity.class));

    }

    @Test
    public void deveConverterEntradaParaEntityNull() {
        // GIVEN
        RegistroEntrada entrada = null;
        //WHEN
        RegistroEntity entity = mapper.mapToEntity(entrada);
        // THEN
        Assert.assertNull(entity);
    }

    @Test
    public void deveConverterEntityParaSaidaNull() {
        // GIVEN
        RegistroEntity entity = null;
        //WHEN
        RegistroSaida saida = mapper.mapToSaida(entity);
        // THEN
        Assert.assertNull(saida);
    }

    @Test
    public void deveConverterEntityParaLogNull() {
        // GIVEN
        RegistroEntity entity = null;
        //WHEN
        RegistroLogHora log = mapper.mapToLog(entity);
        // THEN
        Assert.assertNull(log);
    }
}