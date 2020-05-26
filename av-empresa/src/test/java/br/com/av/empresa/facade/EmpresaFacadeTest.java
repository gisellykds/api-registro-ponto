package br.com.av.empresa.facade;

import br.com.av.empresa.model.EmpresaEntity;
import br.com.av.empresa.model.EmpresaEntrada;
import br.com.av.empresa.model.EmpresaSaida;
import br.com.av.empresa.repository.EmpresaRepository;
import br.com.av.empresa.templates.EmpresaEntityTemplate;
import br.com.av.empresa.templates.EmpresaEntradaTemplate;
import br.com.av.empresa.templates.EmpresaSaidaTemplate;
import br.com.av.empresa.utils.exceptions.ObjInexistenteException;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class EmpresaFacadeTest {

    @InjectMocks
    private EmpresaFacade empresaFacade;

    @Mock
    EmpresaRepository empresaRepository;

    @Before
    public void init() {
        FixtureFactoryLoader.loadTemplates("br.com.av.empresa.templates");
    }

    @Test
    public void deveSalvar() {
        //given
        EmpresaEntrada empresaEntrada = Fixture.from(EmpresaEntrada.class).gimme(EmpresaEntradaTemplate.EMPRESA_ENTRADA_VALIDO);
        EmpresaEntity empresaEntity = Fixture.from(EmpresaEntity.class).gimme(EmpresaEntityTemplate.EMPRESA_ENTITY_VALIDO);
        EmpresaSaida empresaSaida = Fixture.from(EmpresaSaida.class).gimme(EmpresaSaidaTemplate.EMPRESA_SAIDA_VALIDO);
        Mockito.when(empresaRepository.save(any(EmpresaEntity.class))).thenReturn(empresaEntity);
        //when
        EmpresaSaida saida = empresaFacade.salvar(empresaEntrada);
        //then
        Assert.assertNotNull(saida);
        Assert.assertEquals(empresaSaida.getId(), saida.getId());
        Assert.assertEquals(empresaSaida.getNome(), saida.getNome(), empresaEntrada.getNome());
        Assert.assertEquals(empresaSaida.getLocalidade(), saida.getLocalidade(), empresaEntrada.getLocalidade());
        Assert.assertEquals(empresaSaida.getRamoAtividade(), saida.getRamoAtividade(), empresaEntrada.getRamoAtividade());
        verify(empresaRepository, times(1)).save(Mockito.any(EmpresaEntity.class));

    }

    @Test
    public void deveListar() {
        //given
        List<EmpresaEntity> empresaEntities = Fixture.from(EmpresaEntity.class).gimme(1, EmpresaEntityTemplate.EMPRESA_ENTITY_VALIDO);
        List<EmpresaSaida> saidaEsperada = Fixture.from(EmpresaSaida.class).gimme(1, EmpresaSaidaTemplate.EMPRESA_SAIDA_VALIDO);
        Mockito.when(empresaRepository.findAll()).thenReturn(empresaEntities);
        //when
        List<EmpresaSaida> saida = empresaFacade.listar();
        //then
        Assert.assertNotNull(saida);
        Assert.assertEquals(saidaEsperada.get(0).getId(), saida.get(0).getId(), empresaEntities.get(0).getId());
        Assert.assertEquals(saidaEsperada.get(0).getNome(), saida.get(0).getNome(), empresaEntities.get(0).getNome());
        Assert.assertEquals(saidaEsperada.get(0).getLocalidade(), saida.get(0).getLocalidade(), empresaEntities.get(0).getLocalidade());
        Assert.assertEquals(saidaEsperada.get(0).getRamoAtividade(), saida.get(0).getRamoAtividade(), empresaEntities.get(0).getRamoAtividade());
        verify(empresaRepository, times(1)).findAll();

    }

    @Test(expected = ObjInexistenteException.class)
    public void deveListarException() {
        Mockito.when(empresaRepository.findAll()).thenReturn(Collections.emptyList());
        //when
        List<EmpresaSaida> listaEmpresa = empresaFacade.listar();
        //then
        Assert.assertNull(listaEmpresa);
    }

    @Test
    public void deveAtualizarRamo() {
        //given
        EmpresaEntrada empresaEntrada = Fixture.from(EmpresaEntrada.class).gimme(EmpresaEntradaTemplate.EMPRESA_ENTRADA_VALIDO_RAMO);
        EmpresaEntity empresaEntity = Fixture.from(EmpresaEntity.class).gimme(EmpresaEntityTemplate.EMPRESA_ENTITY_VALIDO_RAMO);
        EmpresaSaida empresaSaida = Fixture.from(EmpresaSaida.class).gimme(EmpresaSaidaTemplate.EMPRESA_SAIDA_RAMO);
        Long id = 1L;
        Mockito.when(empresaRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.ofNullable(empresaEntity));
        Mockito.when(empresaRepository.save(any(EmpresaEntity.class))).thenReturn(empresaEntity);
        //when
        EmpresaSaida saida = empresaFacade.atualiza(id, empresaEntrada);
        //then
        Assert.assertNotNull(saida);
        Assert.assertEquals(empresaSaida.getId(), saida.getId());
        Assert.assertEquals(empresaSaida.getRamoAtividade(), saida.getRamoAtividade(), empresaEntrada.getRamoAtividade());
        Assert.assertEquals(empresaSaida.getLocalidade(), saida.getLocalidade());
        Assert.assertEquals(empresaSaida.getNome(), saida.getNome());
        verify(empresaRepository, times(1)).findById(Mockito.anyLong());
        verify(empresaRepository, times(1)).save(Mockito.any(EmpresaEntity.class));
    }

    @Test(expected = ObjInexistenteException.class)
    public void deveAtualizarRamoException() {
        //given
        EmpresaEntrada empresaEntrada = Fixture.from(EmpresaEntrada.class).gimme(EmpresaEntradaTemplate.EMPRESA_ENTRADA_VALIDO);
        EmpresaEntity empresaEntity = Fixture.from(EmpresaEntity.class).gimme(EmpresaEntityTemplate.EMPRESA_ENTITY_VALIDO);
        Long id = 4L;
        Mockito.when(empresaRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Mockito.when(empresaRepository.save(any(EmpresaEntity.class))).thenReturn(empresaEntity);
        //when
        EmpresaSaida saida = empresaFacade.atualiza(id, empresaEntrada);
        //then
        Assert.assertNull(saida);
    }

    @Test(expected = ObjInexistenteException.class)
    public void deveBuscarEmpresaIdException(){
        Mockito.when(empresaRepository.findById(anyLong())).thenReturn(Optional.empty());
        //when
        EmpresaSaida saida = empresaFacade.buscaId(2l);
        //then
        Assert.assertNull(saida);
    }

    @Test
    public void deveBuscarEmpresaId(){
        //given
        EmpresaEntity empresaEntity = Fixture.from(EmpresaEntity.class).gimme(EmpresaEntityTemplate.EMPRESA_ENTITY_VALIDO);
        EmpresaSaida empresaSaida = Fixture.from(EmpresaSaida.class).gimme(EmpresaSaidaTemplate.EMPRESA_SAIDA_VALIDO);
        Long id = 1L;
        Mockito.when(empresaRepository.findById(anyLong())).thenReturn(Optional.ofNullable(empresaEntity));
        //when
        EmpresaSaida saida = empresaFacade.buscaId(id);
        //then
        Assert.assertNotNull(saida);
        Assert.assertEquals(empresaSaida.getId(), saida.getId(), empresaEntity.getId());
        Assert.assertEquals(empresaSaida.getNome(), saida.getNome(), empresaEntity.getNome());
        Assert.assertEquals(empresaSaida.getLocalidade(), saida.getLocalidade(), empresaEntity.getLocalidade());
        Assert.assertEquals(empresaSaida.getRamoAtividade(), saida.getRamoAtividade(), empresaEntity.getRamoAtividade());
        verify(empresaRepository, times(1)).findById(anyLong());
    }

}