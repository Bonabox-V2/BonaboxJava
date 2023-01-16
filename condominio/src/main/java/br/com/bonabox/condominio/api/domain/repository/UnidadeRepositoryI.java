package br.com.bonabox.condominio.api.domain.repository;

import br.com.bonabox.condominio.api.domain.repository.entity.UnidadeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnidadeRepositoryI extends JpaRepository<UnidadeEntity, Integer>{

	@Query(value = "SELECT u.unidade_id, u.piso, u.numero_unidade, u.label_unidade "
			+ "FROM db_condominio.condominio c "
			+ "INNER JOIN db_condominio.condominio_composicao cc "
			+ "INNER JOIN unidade u "
			+ "WHERE c.condominio_id = ?1 "
			+ "AND cc.ala_id = ?2 "
			+ "AND cc.bloco_id = ?3 "
			+ "AND c.condominio_id = cc.condominio_id "
			+ "AND u.unidade_id = cc.unidade_id", nativeQuery = true)
	List<UnidadeEntity> findUnidadeByCondominioId(Integer condominioId, Integer alaId, Integer blocoId);
	
}
