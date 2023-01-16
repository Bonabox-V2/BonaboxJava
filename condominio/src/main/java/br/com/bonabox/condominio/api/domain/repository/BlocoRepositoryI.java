package br.com.bonabox.condominio.api.domain.repository;

import br.com.bonabox.condominio.api.domain.Bloco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlocoRepositoryI extends JpaRepository<Bloco, Integer>{

    @Query(value = "SELECT distinct(b.bloco_id), b.label, b.nome, b.descricao "
            + "FROM db_condominio.condominio "
            + "c INNER JOIN db_condominio.condominio_composicao cc "
            + "INNER JOIN bloco b "
            + "WHERE c.condominio_id = ?1 "
            + "AND cc.ala_id = ?2 "
            + "AND c.condominio_id = cc.condominio_id "
            + "AND b.bloco_id = cc.bloco_id", nativeQuery = true)
    List<Bloco> findBlocoByCondominioId(Integer condominioId, Integer alaId);

}
