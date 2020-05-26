package br.com.av.gateway.service;

import br.com.av.gateway.bean.EmpresaFuncionario;
import br.com.av.gateway.bean.FuncionarioEmpresa;
import br.com.av.gateway.empresa.bean.EmpresaSaida;
import br.com.av.gateway.empresa.service.EmpresaService;
import br.com.av.gateway.error.CustomErrorDecoder;
import br.com.av.gateway.funcionario.bean.FuncionarioSaida;
import br.com.av.gateway.funcionario.bean.FuncionarioEntrada;
import br.com.av.gateway.funcionario.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FuncionarioGatewayService {
    private FuncionarioService funcionarioService;
    private EmpresaService empresaService;

    @Autowired
    public FuncionarioGatewayService(FuncionarioService funcionarioService, EmpresaService empresaService){
        this.funcionarioService = funcionarioService;
        this.empresaService = empresaService;
    }

    public FuncionarioEmpresa salvar(FuncionarioEntrada funcionarioEntrada){
        EmpresaSaida empresa = empresaService.buscarId(funcionarioEntrada.getEmpresaId());
        if (empresa.equals(null))
            throw new CustomErrorDecoder.ObjInexistenteException("Empresa não cadastrada.");

        FuncionarioSaida funcionario = funcionarioService.salvar(funcionarioEntrada);
        FuncionarioEmpresa funcionarioEmpresa = new FuncionarioEmpresa(funcionario, empresa);
        return funcionarioEmpresa;
    }

    public List<FuncionarioSaida> listar() {
        List<FuncionarioSaida> funcionarios = funcionarioService.listar();
        return funcionarios;
    }

    public EmpresaFuncionario listarEmpresasEFuncionarios(Long empresaId){
        EmpresaSaida empresa = empresaService.buscarId(empresaId);
        if (empresa.equals(null))
            throw new CustomErrorDecoder.ObjInexistenteException("Empresa não cadastrada.");

        List<FuncionarioSaida> funcionarios = funcionarioService.listarFuncionariosEmpresa(empresaId);
        EmpresaFuncionario empresaFuncionario = new EmpresaFuncionario(empresa, funcionarios);
        return empresaFuncionario;
    }

    public FuncionarioEmpresa atualizarCargo(Long id, FuncionarioEntrada funcionarioEntrada) {
        FuncionarioSaida funcionario = funcionarioService.atualizaCargo(id, funcionarioEntrada);
        EmpresaSaida empresa = empresaService.buscarId(funcionario.getEmpresaId());

        FuncionarioEmpresa funcionarioEmpresa = new FuncionarioEmpresa(funcionario, empresa);
        return funcionarioEmpresa;
    }

    public FuncionarioEmpresa buscarFuncionarioId(Long id) {
        FuncionarioSaida funcionario = funcionarioService.buscaFuncionarioId(id);
        EmpresaSaida empresa = empresaService.buscarId(funcionario.getEmpresaId());
        FuncionarioEmpresa funcionarioEmpresa = new FuncionarioEmpresa(funcionario, empresa);
        return funcionarioEmpresa;
    }
}
