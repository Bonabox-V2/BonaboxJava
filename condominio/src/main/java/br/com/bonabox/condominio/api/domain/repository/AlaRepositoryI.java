package br.com.bonabox.condominio.api.domain.repository;

import br.com.bonabox.condominio.api.domain.Ala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlaRepositoryI extends JpaRepository<Ala, Integer> {

    @Query(value = "SELECT distinct(a.ala_id), a.label, a.nome, a.descricao "
            + "FROM db_condominio.condominio c "
            + "INNER JOIN db_condominio.condominio_composicao cc "
            + "INNER JOIN ala a "
            + "WHERE c.condominio_id = ?1 "
            + "AND c.condominio_id = cc.condominio_id "
            + "AND a.ala_id = cc.ala_id", nativeQuery = true)
    List<Ala> findAlaByCondominioId(Integer condominioId);

}
