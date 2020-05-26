package br.com.av.registro.facade;

import br.com.av.registro.model.RegistroEntity;
import br.com.av.registro.model.RegistroEntrada;
import br.com.av.registro.model.RegistroLogHora;
import br.com.av.registro.model.RegistroSaida;
import br.com.av.registro.repository.RegistroRepository;
import br.com.av.registro.templates.RegistroEntityTemplate;
import br.com.av.registro.templates.RegistroEntradaTemplate;
import br.com.av.registro.templates.RegistroLogTemplate;
import br.com.av.registro.templates.RegistroSaidaTemplate;
import br.com.av.registro.utils.exception.ObjInexistenteException;
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
import java.util.Date;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class RegistroFacadeTest {

    @InjectMocks
    private RegistroFacade registroFacade;

    @Mock
    RegistroRepository registroRepository;

    @Before
    public void init() {
        FixtureFactoryLoader.loadTemplates("br.com.av.registro.templates");
    }

    @Test
    public void deveSalvar(){
        //given
        RegistroEntrada registroEntrada = Fixture.from(RegistroEntrada.class).gimme(RegistroEntradaTemplate.REGISTRO_ENTRADA_VALIDO);
        RegistroEntity registroEntity = Fixture.from(RegistroEntity.class).gimme(RegistroEntityTemplate.REGISTRO_ENTITY_VALIDO);
        RegistroSaida registroSaida = Fixture.from(RegistroSaida.class).gimme(RegistroSaidaTemplate.REGISTRO_SAIDA_VALIDO);
        Long funcionarioId = 1L; Long empresaId = 1L;
        when(registroRepository.save(any(RegistroEntity.class))).thenReturn(registroEntity);
        //when
        RegistroSaida saida = registroFacade.salvar(registroEntrada, empresaId, funcionarioId);
        //then
        Assert.assertNotNull(saida);
        Assert.assertEquals(saida.getData(), registroSaida.getData());
        Assert.assertEquals(saida.getLastUpdate(), registroSaida.getLastUpdate());
        Assert.assertEquals(saida.getId(), registroSaida.getId());
        Assert.assertEquals(saida.getEmpresaId(), registroSaida.getEmpresaId());
        Assert.assertEquals(saida.getFuncionarioId(), registroSaida.getFuncionarioId());
        Assert.assertEquals(saida.getMinutos(), registroSaida.getMinutos());
        Assert.assertEquals(saida.getTarefa(), registroSaida.getTarefa());
        verify(registroRepository, times(1)).save(any(RegistroEntity.class));
    }

    @Test
    public void deveListarFuncionarioIdEData() {
        //given
        List<RegistroEntity> registroEntity = Fixture.from(RegistroEntity.class).gimme(1, RegistroEntityTemplate.REGISTRO_ENTITY_VALIDO);
        List<RegistroSaida> registroSaida = Fixture.from(RegistroSaida.class).gimme(1, RegistroSaidaTemplate.REGISTRO_SAIDA_VALIDO);
        Mockito.when(registroRepository.findByDataAndFuncionarioId(any(), anyLong())).thenReturn(registroEntity);
        Long funcionarioId = 1L; Date data = Date.from(new Date().toInstant());
        //when
        List<RegistroSaida> saida = registroFacade.listaFuncionarioIdEData(funcionarioId, data);
        //then
        Assert.assertNotNull(saida);
        Assert.assertEquals(saida.get(0).getData(), registroSaida.get(0).getData());
        Assert.assertEquals(saida.get(0).getLastUpdate(), registroSaida.get(0).getLastUpdate());
        Assert.assertEquals(registroSaida.get(0).getId(), saida.get(0).getId(), registroEntity.get(0).getId());
        Assert.assertEquals(String.valueOf(registroSaida.get(0).getData()), saida.get(0).getData(), registroEntity.get(0).getData());
        Assert.assertEquals(String.valueOf(registroSaida.get(0).getLastUpdate()), saida.get(0).getLastUpdate(), registroEntity.get(0).getLastUpdate());
        Assert.assertEquals(registroSaida.get(0).getTarefa(), saida.get(0).getTarefa(), registroEntity.get(0).getTarefa());
        Assert.assertEquals(registroSaida.get(0).getMinutos(), saida.get(0).getMinutos(), registroEntity.get(0).getMinutos());
        Assert.assertEquals(registroSaida.get(0).getFuncionarioId(), saida.get(0).getFuncionarioId(), registroEntity.get(0).getFuncionarioId());
        Assert.assertEquals(registroSaida.get(0).getEmpresaId(), saida.get(0).getEmpresaId(), registroEntity.get(0).getEmpresaId());
        verify(registroRepository, times(1)).findByDataAndFuncionarioId(any(), anyLong());
    }

    @Test(expected = ObjInexistenteException.class)
    public void deveListarFuncionarioIdEDataException() throws Exception {
        //given
        Mockito.when(registroRepository.findByDataAndFuncionarioId(any(), anyLong())).thenReturn(Collections.emptyList());
        Long funcionarioId = 3L; Date data = Date.from(new Date().toInstant());
        //when
        List<RegistroSaida> saida = registroFacade.listaFuncionarioIdEData(funcionarioId, data);
        //then
        Assert.assertNull(saida);
    }

    @Test
    public void deveGerarLogHoraData(){
        //given
        List<RegistroEntity> registroEntity = Fixture.from(RegistroEntity.class).gimme(1, RegistroEntityTemplate.REGISTRO_ENTITY_VALIDO);
        RegistroLogHora registroLogHora = Fixture.from(RegistroLogHora.class).gimme(RegistroLogTemplate.REGISTRO_LOG_VALIDO);
        Mockito.when(registroRepository.findByDataAndFuncionarioId(any(), anyLong())).thenReturn(registroEntity);
        Long funcionarioId = 1L; Date data = Date.from(new Date().toInstant());
        //when
        RegistroLogHora saida = registroFacade.gerarLogHoraData(funcionarioId, data);
        //then
        Assert.assertNotNull(saida);
        Assert.assertEquals(saida.getData(), registroLogHora.getData());
        Assert.assertEquals(saida.getHoras(), registroLogHora.getHoras());
        Assert.assertEquals(saida.getEmpresaId(), registroLogHora.getEmpresaId());
        Assert.assertEquals(saida.getFuncionarioId(), registroLogHora.getFuncionarioId());
        verify(registroRepository, times(1)).findByDataAndFuncionarioId(any(), anyLong());
    }

    @Test(expected = ObjInexistenteException.class)
    public void deveGerarLogHoraDataException(){
        //given
        Mockito.when(registroRepository.findByDataAndFuncionarioId(any(), anyLong())).thenReturn(Collections.emptyList());
        Long funcionarioId = 3L; Date data = Date.from(new Date().toInstant());
        //when
        RegistroLogHora saida = registroFacade.gerarLogHoraData(funcionarioId, data);
        //then
        Assert.assertNull(saida);
    }
    @Test
    public void deveConverterMinutoHora(){
        //GIVEN
        Integer min = 60;
        //WHEN
        String hora = registroFacade.converterMinHora(min);
        //THEN
        Assert.assertNotNull(hora);
        Assert.assertEquals("1:0", hora);
    }
}
