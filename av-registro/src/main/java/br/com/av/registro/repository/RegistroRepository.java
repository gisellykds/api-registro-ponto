package br.com.av.registro.repository;

import br.com.av.registro.model.RegistroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface RegistroRepository extends JpaRepository<RegistroEntity, Long> {

    List<RegistroEntity> findByDataAndFuncionarioId(Date data, Long funcionarioId);
}
