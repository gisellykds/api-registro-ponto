package br.com.av.registro.controller;

import br.com.av.registro.facade.RegistroFacade;
import br.com.av.registro.model.RegistroEntrada;
import br.com.av.registro.model.RegistroLogHora;
import br.com.av.registro.model.RegistroSaida;
import br.com.av.registro.templates.RegistroEntradaTemplate;
import br.com.av.registro.templates.RegistroLogTemplate;
import br.com.av.registro.templates.RegistroSaidaTemplate;
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
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class RegistroControllerTest {

    @InjectMocks
    private RegistroController registroController;

    @Mock
    private RegistroFacade registroFacade;

    private MockMvc mockMvc;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(registroController).build();
        FixtureFactoryLoader.loadTemplates("br.com.av.registro.templates");
    }

    @Test
    public void deveSalvar() throws Exception {
        //given
        RegistroSaida registroSaida = Fixture.from(RegistroSaida.class).gimme(RegistroSaidaTemplate.REGISTRO_SAIDA_VALIDO);
        RegistroEntrada registroEntrada = Fixture.from(RegistroEntrada.class).gimme(RegistroEntradaTemplate.REGISTRO_ENTRADA_VALIDO);
        Long empresaId = 1L; Long funcionarioId = 1L;
        when(registroFacade.salvar(Mockito.any(RegistroEntrada.class), anyLong(), anyLong())).thenReturn(registroSaida);
        //when
        MvcResult result = mockMvc.perform(post("/api/registro/" + empresaId + "/" + funcionarioId)
                .content(asJsonString(registroEntrada))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        RegistroSaida saida = new ObjectMapper().readValue(json, RegistroSaida.class);
        //then
        Assert.assertNotNull(saida);
        Assert.assertEquals(saida.getId(), registroSaida.getId());
        Assert.assertEquals(saida.getEmpresaId(), registroSaida.getEmpresaId());
        Assert.assertEquals(saida.getFuncionarioId(), registroSaida.getFuncionarioId());
        Assert.assertEquals(saida.getMinutos(), registroSaida.getMinutos());
        Assert.assertEquals(saida.getTarefa(), registroSaida.getTarefa());
        verify(registroFacade, times(1)).salvar(any(RegistroEntrada.class), anyLong(), anyLong());
    }

    @Test
    public void devebuscarRegistroFuncionarioData() throws Exception {
        //given
        List<RegistroSaida> registroSaida = Fixture.from(RegistroSaida.class).gimme(1, RegistroSaidaTemplate.REGISTRO_SAIDA_VALIDO);
        Long funcionarioId = 1L;
        LocalDate data = LocalDate.now();
        when(registroFacade.listaFuncionarioIdEData(anyLong(), any())).thenReturn(registroSaida);
        //when
        MvcResult result = mockMvc.perform(get("/api/registro/" + funcionarioId)
                .param("data", String.valueOf(data))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        List<RegistroSaida> saida = Arrays.asList(new ObjectMapper().readValue(json, RegistroSaida[].class));
        //then
        Assert.assertNotNull(saida);
        Assert.assertEquals(saida.size(), registroSaida.size());
        Assert.assertEquals(saida.get(0).getId(), registroSaida.get(0).getId());
        Assert.assertEquals(saida.get(0).getTarefa(), registroSaida.get(0).getTarefa());
        Assert.assertEquals(saida.get(0).getMinutos(), registroSaida.get(0).getMinutos());
        Assert.assertEquals(saida.get(0).getFuncionarioId(), registroSaida.get(0).getFuncionarioId());
        Assert.assertEquals(saida.get(0).getEmpresaId(), registroSaida.get(0).getEmpresaId());
        verify(registroFacade, times(1)).listaFuncionarioIdEData(anyLong(), any());
    }

    @Test
    public void deveBuscarTotalHoraPeriodo() throws Exception {
        //given
        RegistroLogHora registroLogsaida = Fixture.from(RegistroLogHora.class).gimme(RegistroLogTemplate.REGISTRO_LOG_VALIDO);
        Long funcionarioId = 1L;
        LocalDate data = LocalDate.now();
        when(registroFacade.gerarLogHoraData(anyLong(), any())).thenReturn(registroLogsaida);
        //when
        MvcResult result = mockMvc.perform(get("/api/registro/total-hora-periodo/" + funcionarioId)
                .param("data", String.valueOf(data))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        RegistroLogHora saida = new ObjectMapper().readValue(json, RegistroLogHora.class);
        //then
        Assert.assertNotNull(saida);
        Assert.assertEquals(saida.getHoras(), registroLogsaida.getHoras());
        Assert.assertEquals(saida.getFuncionarioId(), registroLogsaida.getFuncionarioId());
        Assert.assertEquals(saida.getEmpresaId(), registroLogsaida.getEmpresaId());
        Assert.assertEquals(saida.getHoras(), registroLogsaida.getHoras());
        verify(registroFacade, times(1)).gerarLogHoraData(anyLong(), any());

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
