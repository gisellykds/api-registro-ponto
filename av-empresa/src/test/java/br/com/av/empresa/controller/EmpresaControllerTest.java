package br.com.av.empresa.controller;

import br.com.av.empresa.facade.EmpresaFacade;
import br.com.av.empresa.model.EmpresaEntrada;
import br.com.av.empresa.model.EmpresaSaida;
import br.com.av.empresa.templates.EmpresaEntradaTemplate;
import br.com.av.empresa.templates.EmpresaSaidaTemplate;
import br.com.six2six.fixturefactory.Fixture;
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
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class EmpresaControllerTest {

    @InjectMocks
    private EmpresaController empresaController;

    @Mock
    private EmpresaFacade empresaFacade;

    private MockMvc mockMvc;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(empresaController).build();
        FixtureFactoryLoader.loadTemplates("br.com.av.empresa.templates");
    }

    @Test
    public void deveSalvar() throws Exception {
        //given
        EmpresaSaida empresaSaida = Fixture.from(EmpresaSaida.class).gimme(EmpresaSaidaTemplate.EMPRESA_SAIDA_VALIDO);
        EmpresaEntrada empresaEntrada = Fixture.from(EmpresaEntrada.class).gimme(EmpresaEntradaTemplate.EMPRESA_ENTRADA_VALIDO);
        when(empresaFacade.salvar(Mockito.any(EmpresaEntrada.class))).thenReturn(empresaSaida);
        //when
        MvcResult result = mockMvc.perform(post("/api/empresa")
                .content(asJsonString(empresaEntrada))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        EmpresaSaida saida = new ObjectMapper().readValue(json, EmpresaSaida.class);
        //then
        Assert.assertNotNull(saida);
        Assert.assertEquals(saida.getId(), empresaSaida.getId());
        Assert.assertEquals(empresaEntrada.getNome(), saida.getNome(), empresaSaida.getNome());
        Assert.assertEquals(empresaEntrada.getLocalidade(), saida.getLocalidade(), empresaSaida.getLocalidade());
        Assert.assertEquals(empresaEntrada.getRamoAtividade(), saida.getRamoAtividade(), empresaSaida.getRamoAtividade());
        verify(empresaFacade, times(1)).salvar(any(EmpresaEntrada.class));
    }

    @Test
    public void deveListar() throws Exception {
        //given
        List<EmpresaSaida> funcionarioSaida = Fixture.from(EmpresaSaida.class).gimme(1, EmpresaSaidaTemplate.EMPRESA_SAIDA_VALIDO);
        when(empresaFacade.listar()).thenReturn(funcionarioSaida);
        //when
        MvcResult result = mockMvc.perform(get("/api/empresa")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        List<EmpresaSaida> saida = Arrays.asList(new ObjectMapper().readValue(json, EmpresaSaida[].class));
        //then
        Assert.assertNotNull(saida);
        Assert.assertEquals(saida.size(), funcionarioSaida.size());
        Assert.assertEquals(saida.get(0).getId(), funcionarioSaida.get(0).getId());
        Assert.assertEquals(saida.get(0).getNome(), funcionarioSaida.get(0).getNome());
        Assert.assertEquals(saida.get(0).getLocalidade(), funcionarioSaida.get(0).getLocalidade());
        Assert.assertEquals(saida.get(0).getRamoAtividade(), funcionarioSaida.get(0).getRamoAtividade());
        verify(empresaFacade, times(1)).listar();
    }

    @Test
    public void deveBuscarId() throws Exception {
        //given
        Long id = 1L;
        EmpresaSaida funcionarioSaida = Fixture.from(EmpresaSaida.class).gimme(EmpresaSaidaTemplate.EMPRESA_SAIDA_VALIDO);
        when(empresaFacade.buscaId(Mockito.anyLong())).thenReturn(funcionarioSaida);
        //when
        MvcResult result = mockMvc.perform(get("/api/empresa/busca/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        EmpresaSaida saida = new ObjectMapper().readValue(json, EmpresaSaida.class);
        //then
        Assert.assertNotNull(saida);
        Assert.assertEquals(saida.getId(), funcionarioSaida.getId());
        Assert.assertEquals(saida.getNome(), funcionarioSaida.getNome());
        Assert.assertEquals(saida.getRamoAtividade(), funcionarioSaida.getRamoAtividade());
        Assert.assertEquals(saida.getLocalidade(), funcionarioSaida.getLocalidade());
        verify(empresaFacade, times(1)).buscaId(anyLong());
    }

    @Test
    public void deveAtualizarRamo() throws Exception {
        //given
        Long id = 1L;
        EmpresaEntrada empresaEntrada = Fixture.from(EmpresaEntrada.class).gimme(EmpresaEntradaTemplate.EMPRESA_ENTRADA_VALIDO_RAMO);
        EmpresaSaida empresaSaida = Fixture.from(EmpresaSaida.class).gimme(EmpresaSaidaTemplate.EMPRESA_SAIDA_RAMO);
        when(empresaFacade.atualiza(Mockito.anyLong(), Mockito.any(EmpresaEntrada.class))).thenReturn(empresaSaida);
        //when
        MvcResult result = mockMvc.perform(post("/api/empresa/" + id)
                .content(asJsonString(empresaEntrada))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        EmpresaSaida saida = new ObjectMapper().readValue(json, EmpresaSaida.class);
        //then
        Assert.assertNotNull(saida);
        Assert.assertEquals(saida.getId(), empresaSaida.getId());
        Assert.assertEquals(saida.getNome(), empresaSaida.getNome());
        Assert.assertEquals(saida.getLocalidade(), empresaSaida.getLocalidade());
        Assert.assertEquals(saida.getRamoAtividade(), empresaSaida.getRamoAtividade());
        verify(empresaFacade, times(1)).atualiza(anyLong(),  Mockito.any(EmpresaEntrada.class));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
