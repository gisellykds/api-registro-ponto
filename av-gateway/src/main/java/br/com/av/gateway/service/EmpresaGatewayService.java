package br.com.av.gateway.service;

import br.com.av.gateway.empresa.bean.EmpresaSaida;
import br.com.av.gateway.empresa.bean.EmpresaEntrada;
import br.com.av.gateway.empresa.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmpresaGatewayService {

    private EmpresaService empresaService;

    @Autowired
    public EmpresaGatewayService(EmpresaService empresaService){
        this.empresaService = empresaService;
    }

    public EmpresaSaida salvar(EmpresaEntrada empresaEntrada) {
        EmpresaSaida empresa = empresaService.salvar(empresaEntrada);
        return empresa;
    }

    public List<EmpresaSaida> listar() {
        List<EmpresaSaida> empresas = empresaService.listar();
        return empresas;
    }

    public EmpresaSaida buscarId(Long id) {
        EmpresaSaida empresa = empresaService.buscarId(id);
        return empresa;
    }

    public EmpresaSaida atualizarRamo(Long id, EmpresaEntrada empresaEntrada) {
        EmpresaSaida empresa = empresaService.atualizaRamo(id, empresaEntrada);
        return empresa;
    }
}
