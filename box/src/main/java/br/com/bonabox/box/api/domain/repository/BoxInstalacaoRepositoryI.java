package br.com.bonabox.box.api.domain.repository;

import br.com.bonabox.box.api.domain.entity.BoxInstalacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BoxInstalacaoRepositoryI extends JpaRepository<BoxInstalacaoEntity, Integer> {

	@Query("SELECT b FROM BoxInstalacaoEntity b WHERE b.codigoBox = ?1")
	BoxInstalacaoEntity consultarByBoxId(Integer boxId);

}
