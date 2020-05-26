package br.com.av.gateway.empresa.service;

import br.com.av.gateway.empresa.bean.EmpresaSaida;
import br.com.av.gateway.empresa.bean.EmpresaEntrada;
import br.com.av.gateway.empresa.feign.client.EmpresaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Component
public class EmpresaService {

    private EmpresaClient empresaClient;

    @Autowired
    public EmpresaService(EmpresaClient empresaClient){
        this.empresaClient = empresaClient;
    }

    public EmpresaSaida salvar(EmpresaEntrada empresaEntrada){
        return empresaClient.salvar(empresaEntrada);
    }

    public List<EmpresaSaida> listar() {
        return empresaClient.listar();
    }

    public EmpresaSaida buscarId(Long id){
        return empresaClient.buscarId(id);
    }

    public EmpresaSaida atualizaRamo(Long id, EmpresaEntrada empresaEntrada){
        return empresaClient.atualizaRamo(id, empresaEntrada);
    }
}
