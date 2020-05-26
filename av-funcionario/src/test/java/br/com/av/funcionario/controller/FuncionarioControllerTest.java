package br.com.av.funcionario.controller;

import br.com.av.funcionario.facade.FuncionarioFacade;
import br.com.av.funcionario.model.FuncionarioEntrada;
import br.com.av.funcionario.model.FuncionarioSaida;
import br.com.av.funcionario.templates.FuncionarioEntradaTemplate;
import br.com.av.funcionario.templates.FuncionarioSaidaTemplate;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.List;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class FuncionarioControllerTest {
    @InjectMocks
    private FuncionarioController funcionarioController;

    @Mock
    private FuncionarioFacade funcionarioFacade;

    private MockMvc mockMvc;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(funcionarioController).build();
        FixtureFactoryLoader.loadTemplates("br.com.av.funcionario.templates");
    }

    @Test
    public void deveSalvar() throws Exception {
        //given
        FuncionarioSaida funcionarioSaida = Fixture.from(FuncionarioSaida.class).gimme(FuncionarioSaidaTemplate.FUNCIONARIO_SAIDA_VALIDO);
        FuncionarioEntrada funcionarioEntrada = Fixture.from(FuncionarioEntrada.class).gimme(FuncionarioEntradaTemplate.FUNCIONARIO_ENTRADA_VALIDO);
        when(funcionarioFacade.salvar(any(FuncionarioEntrada.class))).thenReturn(funcionarioSaida);
        //when
        MvcResult result = mockMvc.perform(post("/api/funcionario")
                .content(asJsonString(funcionarioEntrada))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        FuncionarioSaida saida = new ObjectMapper().readValue(json, FuncionarioSaida.class);
        //then
        Assert.assertNotNull(saida);
        Assert.assertEquals(saida.getNome(), funcionarioEntrada.getNome());
        Assert.assertEquals(saida.getCpf(), funcionarioEntrada.getCpf());
        Assert.assertEquals(saida.getEmpresaId(), funcionarioEntrada.getEmpresaId());
        Assert.assertEquals(saida.getCargo(), funcionarioEntrada.getCargo());
        Assert.assertEquals(saida.getId(), funcionarioSaida.getId());
        verify(funcionarioFacade, times(1)).salvar(any(FuncionarioEntrada.class));
    }

    @Test
    public void deveListar() throws Exception {
        //given
        List<FuncionarioSaida> funcionarioSaida = Fixture.from(FuncionarioSaida.class).gimme(1, FuncionarioSaidaTemplate.FUNCIONARIO_SAIDA_VALIDO);
        when(funcionarioFacade.listar()).thenReturn(funcionarioSaida);
        //when
        MvcResult result = mockMvc.perform(get("/api/funcionario")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        List<FuncionarioSaida> saida = Arrays.asList(new ObjectMapper().readValue(json, FuncionarioSaida[].class));
        //then
        Assert.assertNotNull(saida);
        Assert.assertEquals(saida.size(), funcionarioSaida.size());
        Assert.assertEquals(saida.get(0).getId(), funcionarioSaida.get(0).getId());
        Assert.assertEquals(saida.get(0).getNome(), funcionarioSaida.get(0).getNome());
        Assert.assertEquals(saida.get(0).getCpf(), funcionarioSaida.get(0).getCpf());
        Assert.assertEquals(saida.get(0).getCargo(), funcionarioSaida.get(0).getCargo());
        Assert.assertEquals(saida.get(0).getEmpresaId(), funcionarioSaida.get(0).getEmpresaId());
        verify(funcionarioFacade, times(1)).listar();
    }

    @Test
    public void deveAtualizarCargo() throws Exception {
        //given
        Long id = 1L;
        FuncionarioEntrada funcionarioEntrada = Fixture.from(FuncionarioEntrada.class).gimme(FuncionarioEntradaTemplate.FUNCIONARIO_ENTRADA_VALIDO_CARGO);
        FuncionarioSaida funcionarioSaida = Fixture.from(FuncionarioSaida.class).gimme(FuncionarioSaidaTemplate.FUNCIONARIO_SAIDA_VALIDO_CARGO);
        when(funcionarioFacade.atualiza(Mockito.anyLong(), Mockito.any(FuncionarioEntrada.class))).thenReturn(funcionarioSaida);
        //when
        MvcResult result = mockMvc.perform(post("/api/funcionario/cargo/" + id)
                .content(asJsonString(funcionarioEntrada))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        FuncionarioSaida saida = new ObjectMapper().readValue(json, FuncionarioSaida.class);
        //then
        Assert.assertNotNull(saida);
        Assert.assertEquals(saida.getId(), funcionarioSaida.getId());
        Assert.assertEquals(saida.getNome(), funcionarioSaida.getNome());
        Assert.assertEquals(saida.getCargo(), funcionarioSaida.getCargo());
        Assert.assertEquals(saida.getCpf(), funcionarioSaida.getCpf());
        Assert.assertEquals(saida.getEmpresaId(), funcionarioSaida.getEmpresaId());
        verify(funcionarioFacade, times(1)).atualiza(anyLong(), any(FuncionarioEntrada.class));
    }

    @Test
    public void deveListarEmpresaId() throws Exception {
        //given
        Long empresaId = 1L;
        List<FuncionarioSaida> funcionarioSaida = Fixture.from(FuncionarioSaida.class).gimme(1, FuncionarioSaidaTemplate.FUNCIONARIO_SAIDA_VALIDO);
        when(funcionarioFacade.listarFuncionarioEmpresa(Mockito.anyLong())).thenReturn(funcionarioSaida);
        //when
        MvcResult result = mockMvc.perform(get("/api/funcionario/empresa/" + empresaId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        List<FuncionarioSaida> saida = Arrays.asList(new ObjectMapper().readValue(json, FuncionarioSaida[].class));
        //then
        Assert.assertNotNull(saida);
        Assert.assertEquals(saida.size(), funcionarioSaida.size());
        Assert.assertEquals(saida.get(0).getId(), funcionarioSaida.get(0).getId());
        Assert.assertEquals(saida.get(0).getNome(), funcionarioSaida.get(0).getNome());
        Assert.assertEquals(saida.get(0).getCpf(), funcionarioSaida.get(0).getCpf());
        Assert.assertEquals(saida.get(0).getCargo(), funcionarioSaida.get(0).getCargo());
        Assert.assertEquals(saida.get(0).getEmpresaId(), funcionarioSaida.get(0).getEmpresaId());
        verify(funcionarioFacade, times(1)).listarFuncionarioEmpresa(anyLong());
    }

    @Test
    public void deveListarFuncId() throws Exception {
        //given
        Long id = 1L;
        FuncionarioSaida funcionarioSaida = Fixture.from(FuncionarioSaida.class).gimme( FuncionarioSaidaTemplate.FUNCIONARIO_SAIDA_VALIDO);
        when(funcionarioFacade.buscaId(anyLong())).thenReturn(funcionarioSaida);
        //when
        MvcResult result = mockMvc.perform(get("/api/funcionario/busca/"+ id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        FuncionarioSaida saida = new ObjectMapper().readValue(json, FuncionarioSaida.class);
        //then
        Assert.assertNotNull(saida);
        Assert.assertEquals(saida.getId(), funcionarioSaida.getId());
        Assert.assertEquals(saida.getNome(), funcionarioSaida.getNome());
        Assert.assertEquals(saida.getCpf(), funcionarioSaida.getCpf());
        Assert.assertEquals(saida.getCargo(), funcionarioSaida.getCargo());
        Assert.assertEquals(saida.getEmpresaId(), funcionarioSaida.getEmpresaId());
        verify(funcionarioFacade, times(1)).buscaId(anyLong());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}