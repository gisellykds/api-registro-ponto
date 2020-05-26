package br.com.av.gateway.service;

import br.com.av.gateway.bean.FuncionarioEmpresa;
import br.com.av.gateway.bean.RegistroEmpresaFuncionario;
import br.com.av.gateway.bean.RegistroLogFuncionarioEmpresa;
import br.com.av.gateway.empresa.bean.EmpresaSaida;
import br.com.av.gateway.empresa.service.EmpresaService;
import br.com.av.gateway.error.CustomErrorDecoder;
import br.com.av.gateway.funcionario.bean.FuncionarioSaida;
import br.com.av.gateway.funcionario.service.FuncionarioService;
import br.com.av.gateway.registro.bean.RegistroSaida;
import br.com.av.gateway.registro.bean.RegistroEntrada;
import br.com.av.gateway.registro.bean.RegistroLogHoraBean;
import br.com.av.gateway.registro.service.RegistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class RegistroGatewayService {
    private RegistroService registroService;
    private EmpresaService empresaService;
    private FuncionarioService funcionarioService;

    @Autowired
    public RegistroGatewayService(RegistroService registroService, EmpresaService empresaService, FuncionarioService funcionarioService ){
        this.registroService = registroService;
        this.empresaService = empresaService;
        this.funcionarioService = funcionarioService;
    }

    public RegistroEmpresaFuncionario salvar(RegistroEntrada registroEntrada, Long empresaId, Long funcionarioId) {
        FuncionarioSaida funcionario = funcionarioService.buscaFuncionarioId(funcionarioId);
        if (!funcionario.getEmpresaId().equals(empresaId))
            throw new CustomErrorDecoder.ObjInexistenteException("Não foi encontrado funcionário com id: " + funcionarioId + " em empresa com id: " + empresaId);
        EmpresaSaida empresa = empresaService.buscarId(empresaId);

        if (empresa.equals(null) && funcionario.equals(null))
            throw new CustomErrorDecoder.ObjInexistenteException("Empresa e funcionario devem ser previamente cadastrados.");

        RegistroSaida registro = registroService.salvar(registroEntrada, empresaId, funcionarioId);
        FuncionarioEmpresa funcionarioEmpresa = new FuncionarioEmpresa(funcionario, empresa);
        RegistroEmpresaFuncionario registroEmpresaFuncionario = new RegistroEmpresaFuncionario(registro, funcionarioEmpresa);
        return registroEmpresaFuncionario;
    }

    public List<RegistroSaida> buscaRegistroFuncIdEData(Long funcionarioId, Date data) {
        FuncionarioSaida funcionario = funcionarioService.buscaFuncionarioId(funcionarioId);
        if (funcionario.equals(null))
            throw new CustomErrorDecoder.ObjInexistenteException("Não há funcionario cadastrado com o id: "+ funcionarioId);

        List<RegistroSaida> registros = registroService.buscaRegistroFuncionarioData(funcionarioId, data);
        return registros;
    }

    public RegistroLogFuncionarioEmpresa buscaTotalHoraPeriodo(Long funcionarioId, Date data){
        FuncionarioSaida funcionario = funcionarioService.buscaFuncionarioId(funcionarioId);
        EmpresaSaida empresa = empresaService.buscarId(funcionario.getEmpresaId());
        if (funcionario.equals(null) && empresa.equals(null))
            throw new CustomErrorDecoder.ObjInexistenteException("Não há funcionario cadastrado com o id: "+ funcionarioId);

        FuncionarioEmpresa funcionarioEmpresa = new FuncionarioEmpresa(funcionario, empresa);
        RegistroLogHoraBean registroLog = registroService.buscaTotalHoraPeriodo(funcionarioId, data);
        RegistroLogFuncionarioEmpresa registroEmpresaFuncionario = new RegistroLogFuncionarioEmpresa(registroLog, funcionarioEmpresa);
        return registroEmpresaFuncionario;
    }
}