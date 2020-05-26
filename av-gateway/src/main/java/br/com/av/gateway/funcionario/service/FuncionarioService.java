package br.com.av.gateway.funcionario.service;

import br.com.av.gateway.funcionario.bean.FuncionarioSaida;
import br.com.av.gateway.funcionario.bean.FuncionarioEntrada;
import br.com.av.gateway.funcionario.feign.client.FuncionarioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {

    private FuncionarioClient funcionarioClient;

    @Autowired
    public FuncionarioService(FuncionarioClient funcionarioClient){
        this.funcionarioClient = funcionarioClient;
    }

    public FuncionarioSaida salvar(FuncionarioEntrada funcionarioEntrada){
        return funcionarioClient.salvar(funcionarioEntrada);
    }

    public List<FuncionarioSaida> listar(){
        return funcionarioClient.listar();
    }

    public FuncionarioSaida buscaFuncionarioId(Long id){
        return funcionarioClient.buscaFuncionarioId(id);
    }

    public List<FuncionarioSaida> listarFuncionariosEmpresa(Long empresaId){
        return funcionarioClient.listarFuncionariosEmpresa(empresaId);
    }

    public FuncionarioSaida atualizaCargo(Long id, FuncionarioEntrada funcionarioEntrada){
        return funcionarioClient.atualizaCargo(id, funcionarioEntrada);
    }
}
