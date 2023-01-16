package br.com.bonabox.condominio.api.domain.repository;

import br.com.bonabox.condominio.api.domain.repository.entity.CondominioComposicaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CondominioComposicaoRepositoryI extends JpaRepository<CondominioComposicaoEntity, Integer> {

	@Query("SELECT c FROM CondominioComposicaoEntity c WHERE c.condominioId = ?1 AND c.alaId = ?2 AND c.blocoId = ?3 AND c.unidadeId = ?4")
	List<CondominioComposicaoEntity> findComposicao(Integer condominioId, Integer alaId, Integer blocoId,
													Integer unidadeId);

}
