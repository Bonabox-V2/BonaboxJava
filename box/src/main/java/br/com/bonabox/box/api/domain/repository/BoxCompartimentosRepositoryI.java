package br.com.bonabox.box.api.domain.repository;

import br.com.bonabox.box.api.domain.entity.BoxCompartimentosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface BoxCompartimentosRepositoryI extends JpaRepository<BoxCompartimentosEntity, Integer> {

	@Query("SELECT c FROM BoxCompartimentosEntity c join c.compartimento e WHERE c.codigoBox = ?1")
	Collection<BoxCompartimentosEntity> consultarCompartimentos(Integer boxId);

}
