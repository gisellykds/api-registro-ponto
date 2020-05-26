package br.com.av.funcionario.facade;

import br.com.av.funcionario.model.FuncionarioEntity;
import br.com.av.funcionario.model.FuncionarioEntrada;
import br.com.av.funcionario.model.FuncionarioSaida;
import br.com.av.funcionario.repository.FuncionarioRepository;
import br.com.av.funcionario.templates.FuncionarioEntityTemplate;
import br.com.av.funcionario.templates.FuncionarioEntradaTemplate;
import br.com.av.funcionario.templates.FuncionarioSaidaTemplate;
import br.com.av.funcionario.utils.exceptions.ObjInexistenteException;
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
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
public class FuncionarioFacadeTest {

    @InjectMocks
    private FuncionarioFacade funcionarioFacade;

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @Before
    public void init() {
        FixtureFactoryLoader.loadTemplates("br.com.av.funcionario.templates");
    }

    @Test
    public void deveSalvar() {
        //given
        FuncionarioEntrada funcionarioEntrada = Fixture.from(FuncionarioEntrada.class).gimme(FuncionarioEntradaTemplate.FUNCIONARIO_ENTRADA_VALIDO);
        FuncionarioEntity funcionarioEntity = Fixture.from(FuncionarioEntity.class).gimme(FuncionarioEntityTemplate.FUNCIONARIO_ENTITY_VALIDO);
        FuncionarioSaida funcionarioSaida = Fixture.from(FuncionarioSaida.class).gimme(FuncionarioSaidaTemplate.FUNCIONARIO_SAIDA_VALIDO);
        when(funcionarioRepository.save(any(FuncionarioEntity.class))).thenReturn(funcionarioEntity);
        //when
        FuncionarioSaida saida = funcionarioFacade.salvar(funcionarioEntrada);
        //then
        Assert.assertNotNull(saida);
        Assert.assertEquals(funcionarioSaida.getId(), saida.getId());
        Assert.assertEquals(funcionarioSaida.getNome(), saida.getNome(), funcionarioEntrada.getNome());
        Assert.assertEquals(funcionarioSaida.getCargo(), saida.getCargo(), funcionarioEntrada.getCargo());
        Assert.assertEquals(funcionarioSaida.getCpf(), saida.getCpf(), funcionarioEntrada.getCpf());
        Assert.assertEquals(funcionarioSaida.getEmpresaId(), saida.getEmpresaId(), funcionarioEntrada.getEmpresaId());
        verify(funcionarioRepository, times(1)).save(any(FuncionarioEntity.class));
    }

    @Test
    public void deveListar() {
        //given
        List<FuncionarioEntity> funcionarioEntities = Fixture.from(FuncionarioEntity.class).gimme(1, FuncionarioEntityTemplate.FUNCIONARIO_ENTITY_VALIDO);
        List<FuncionarioSaida> saidaEsperada = Fixture.from(FuncionarioSaida.class).gimme(1, FuncionarioSaidaTemplate.FUNCIONARIO_SAIDA_VALIDO);
        when(funcionarioRepository.findAll()).thenReturn(funcionarioEntities);
        //when
        List<FuncionarioSaida> saida = funcionarioFacade.listar();
        //then
        Assert.assertNotNull(saida);
        Assert.assertEquals(saidaEsperada.get(0).getId(), saida.get(0).getId());
        Assert.assertEquals(saidaEsperada.get(0).getEmpresaId(), saida.get(0).getEmpresaId());
        Assert.assertEquals(saidaEsperada.get(0).getCpf(), saida.get(0).getCpf());
        Assert.assertEquals(saidaEsperada.get(0).getCargo(), saida.get(0).getCargo());
        Assert.assertEquals(saidaEsperada.get(0).getNome(), saida.get(0).getNome());
        verify(funcionarioRepository, times(1)).findAll();
    }

    @Test(expected = ObjInexistenteException.class)
    public void deveListarException() {
        when(funcionarioRepository.findAll()).thenReturn(Collections.emptyList());
        //when
        List<FuncionarioSaida> listaAlunoSaida = funcionarioFacade.listar();
        //then
        Assert.assertNull(listaAlunoSaida);
    }

    @Test
    public void deveBuscarFuncionariosCpf() {
        //given
        FuncionarioEntity funcionarioEntity = Fixture.from(FuncionarioEntity.class).gimme( FuncionarioEntityTemplate.FUNCIONARIO_ENTITY_VALIDO);
        FuncionarioSaida saidaEsperada = Fixture.from(FuncionarioSaida.class).gimme( FuncionarioSaidaTemplate.FUNCIONARIO_SAIDA_VALIDO);
        when(funcionarioRepository.findById(anyLong())).thenReturn(Optional.ofNullable(funcionarioEntity));
        Long id = 1L;
        //when
        FuncionarioSaida saida = funcionarioFacade.buscaId(id);
        //then
        Assert.assertNotNull(saida);
        Assert.assertEquals(saidaEsperada.getId(), saida.getId());
        Assert.assertEquals(saidaEsperada.getEmpresaId(), saida.getEmpresaId());
        Assert.assertEquals(saidaEsperada.getCpf(), saida.getCpf());
        Assert.assertEquals(saidaEsperada.getCargo(), saida.getCargo());
        Assert.assertEquals(saidaEsperada.getNome(), saida.getNome());
        verify(funcionarioRepository, times(1)).findById(anyLong());
    }

    @Test(expected = ObjInexistenteException.class)
    public void deveBuscarFuncionariosCpfException(){
        when(funcionarioRepository.findById(anyLong())).thenReturn(Optional.empty());
        Long id = 3L;
        //when
        FuncionarioSaida saida = funcionarioFacade.buscaId(id);
        //then
        Assert.assertNull(saida);
    }

    @Test
    public void deveBuscarFuncionariosEmpresaId(){
        //given
        List<FuncionarioEntity> funcionarioEntities = Fixture.from(FuncionarioEntity.class).gimme(1, FuncionarioEntityTemplate.FUNCIONARIO_ENTITY_VALIDO);
        List<FuncionarioSaida> saidaEsperada = Fixture.from(FuncionarioSaida.class).gimme(1, FuncionarioSaidaTemplate.FUNCIONARIO_SAIDA_VALIDO);
        when(funcionarioRepository.findByEmpresaId(anyLong())).thenReturn(funcionarioEntities);
        Long empresaId = 1L;
        //when
        List<FuncionarioSaida> saida = funcionarioFacade.listarFuncionarioEmpresa(empresaId);
        //then
        Assert.assertNotNull(saida);
        Assert.assertEquals(saidaEsperada.get(0).getId(), saida.get(0).getId());
        Assert.assertEquals(saidaEsperada.get(0).getEmpresaId(), saida.get(0).getEmpresaId());
        Assert.assertEquals(saidaEsperada.get(0).getCpf(), saida.get(0).getCpf());
        Assert.assertEquals(saidaEsperada.get(0).getCargo(), saida.get(0).getCargo());
        Assert.assertEquals(saidaEsperada.get(0).getNome(), saida.get(0).getNome());
        verify(funcionarioRepository, times(1)).findByEmpresaId(Mockito.anyLong());
    }

    @Test(expected = ObjInexistenteException.class)
    public void deveBuscarFuncionariosEmpresaIdException(){
        when(funcionarioRepository.findByEmpresaId(anyLong())).thenReturn(Collections.emptyList());
        Long empresaId = 2L;
        //when
        List<FuncionarioSaida> saida = funcionarioFacade.listarFuncionarioEmpresa(empresaId);
        //then
        Assert.assertNull(saida);
    }

    @Test
    public void deveAtualizarCargo() {
        //given
        FuncionarioEntrada funcionarioEntrada = Fixture.from(FuncionarioEntrada.class).gimme(FuncionarioEntradaTemplate.FUNCIONARIO_ENTRADA_VALIDO_CARGO);
        FuncionarioEntity funcionarioEntity = Fixture.from(FuncionarioEntity.class).gimme(FuncionarioEntityTemplate.FUNCIONARIO_ENTITY_VALIDO_CARGO);
        when(funcionarioRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(funcionarioEntity));
        when(funcionarioRepository.save(any(FuncionarioEntity.class))).thenReturn(funcionarioEntity);
        Long id = 1L;
        //when
        FuncionarioSaida saida = funcionarioFacade.atualiza(id, funcionarioEntrada);
        //then
        Assert.assertNotNull(saida);
        Assert.assertEquals(funcionarioEntity.getId(), saida.getId());
        Assert.assertEquals(funcionarioEntity.getCpf(), saida.getCpf());
        Assert.assertEquals(funcionarioEntity.getEmpresaId(), saida.getEmpresaId());
        Assert.assertEquals(funcionarioEntity.getCargo(), saida.getCargo(), funcionarioEntrada.getCargo());
        verify(funcionarioRepository, times(1)).findById(anyLong());
        verify(funcionarioRepository, times(1)).save(any(FuncionarioEntity.class));
    }

    @Test(expected = ObjInexistenteException.class)
    public void deveAtualizarCargoExceptionID() {
        //given
        FuncionarioEntrada funcionarioEntrada = Fixture.from(FuncionarioEntrada.class).gimme(FuncionarioEntradaTemplate.FUNCIONARIO_ENTRADA_VALIDO_CARGO);
        FuncionarioEntity funcionarioEntity = Fixture.from(FuncionarioEntity.class).gimme(FuncionarioEntityTemplate.FUNCIONARIO_ENTITY_VALIDO_CARGO);
        when(funcionarioRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        when(funcionarioRepository.save(any(FuncionarioEntity.class))).thenReturn(funcionarioEntity);
        Long id = 4L;
        //when
        FuncionarioSaida saida = funcionarioFacade.atualiza(id, funcionarioEntrada);
        //then
        Assert.assertNull(saida);
    }

}
