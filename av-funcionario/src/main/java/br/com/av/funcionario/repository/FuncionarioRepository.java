package br.com.av.funcionario.repository;

import br.com.av.funcionario.model.FuncionarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FuncionarioRepository extends JpaRepository<FuncionarioEntity, Long> {

    List<FuncionarioEntity> findByEmpresaId(Long empresaId);
}
