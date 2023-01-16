package br.com.bonabox.box.api.domain.repository;


import br.com.bonabox.box.api.domain.entity.BoxEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface BoxRepositoryI extends JpaRepository<BoxEntity, Integer> {

	@Query("SELECT b FROM BoxEntity b WHERE b.numeroSerial = ?1")
	BoxEntity findBoxBySerialNumber(String numeroSerial);
	
	@Query("SELECT b FROM BoxEntity b WHERE b.numeroSerial = ?1 and b.tipo = ?2")
	BoxEntity findBoxBySerialNumberAndTipo(String numeroSerial, String tipo);
	
	@Query("SELECT b FROM BoxEntity b WHERE b.owner = ?1")
	Collection<BoxEntity> findBoxAdicionalByOwner(Integer owner);

}
