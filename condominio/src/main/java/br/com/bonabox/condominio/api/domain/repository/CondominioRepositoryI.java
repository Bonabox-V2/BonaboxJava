package br.com.bonabox.condominio.api.domain.repository;

import br.com.bonabox.condominio.api.domain.repository.entity.CondominioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CondominioRepositoryI extends JpaRepository<CondominioEntity, Integer> {

}
