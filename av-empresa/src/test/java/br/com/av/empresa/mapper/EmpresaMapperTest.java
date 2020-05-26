package br.com.av.empresa.mapper;
import br.com.av.empresa.facade.EmpresaFacade;
import br.com.av.empresa.model.EmpresaEntity;
import br.com.av.empresa.model.EmpresaEntrada;
import br.com.av.empresa.model.EmpresaSaida;
import br.com.av.empresa.templates.EmpresaEntityTemplate;
import br.com.av.empresa.templates.EmpresaEntradaTemplate;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
public class EmpresaMapperTest {

    @Spy
    private EmpresaMapper mapper = Mappers.getMapper(EmpresaMapper.class);

    @InjectMocks
    private EmpresaFacade empresaFacade;

    @Before
    public void init() {
        FixtureFactoryLoader.loadTemplates("br.com.av.empresa.templates");
    }

    @Test
    public void deveConverterEntradaParaEntity() {
        // GIVEN
        EmpresaEntrada entrada = Fixture.from(EmpresaEntrada.class).gimme(EmpresaEntradaTemplate.EMPRESA_ENTRADA_VALIDO);
        //WHEN
        EmpresaEntity entity = mapper.mapToEntity(entrada);
        // THEN
        Assert.assertNotNull(entity);
        Assert.assertEquals(entrada.getNome(), entity.getNome());
        Assert.assertEquals(entrada.getLocalidade(), entity.getLocalidade());
        Assert.assertEquals(entrada.getRamoAtividade(), entity.getRamoAtividade());
        verify(mapper, times(1)).mapToEntity(any(EmpresaEntrada.class));
    }

    @Test
    public void deveConverterEntityParaSaida() {
        //GIVEN
        EmpresaEntity entity =  Fixture.from(EmpresaEntity.class).gimme(EmpresaEntityTemplate.EMPRESA_ENTITY_VALIDO);
        //WHEN
        EmpresaSaida saida = mapper.mapToSaida(entity);
        //THEN
        Assert.assertNotNull(saida);
        Assert.assertEquals(entity.getId(), saida.getId());
        Assert.assertEquals(entity.getNome(), saida.getNome());
        Assert.assertEquals(entity.getLocalidade(), saida.getLocalidade());
        Assert.assertEquals(entity.getRamoAtividade(), saida.getRamoAtividade());
        verify(mapper, times(1)).mapToSaida(any(EmpresaEntity.class));
    }

    @Test
    public void deveConverterEntradaParaEntityNull() {
        // GIVEN
        EmpresaEntrada entrada = null;
        //WHEN
        EmpresaEntity entity = mapper.mapToEntity(entrada);
        // THEN
        Assert.assertNull(entity);
    }

    @Test
    public void deveConverterEntityParaSaidaNull() {
        // GIVEN
        EmpresaEntity entity = null;
        //WHEN
        EmpresaSaida saida = mapper.mapToSaida(entity);
        // THEN
        Assert.assertNull(saida);
    }
}
