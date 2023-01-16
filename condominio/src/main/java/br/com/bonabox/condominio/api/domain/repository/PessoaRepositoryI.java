package br.com.bonabox.condominio.api.domain.repository;

import br.com.bonabox.condominio.api.domain.repository.entity.PessoaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaRepositoryI extends JpaRepository<PessoaEntity, Integer>{

	//@Query(value = "SELECT cp.pessoa_id, cp.nome, cp.apelido, cp.documento, cp.tipo_documento, cp.numero_celular, cp.email "
	@Query(value = "SELECT cp.pessoa_id, cp.nome, cp.numero_celular, cp.email, eh_principal "
			+ "FROM condominio_composicao c "
			+ "INNER JOIN condominio_moradores m "
			+ "INNER JOIN pessoa cp "
			+ "WHERE c.condominio_id = ?1 "
			+ "AND c.ala_id = ?2 "
			+ "AND c.bloco_id = ?3 "
			+ "AND c.unidade_id = ?4 "
			+ "AND m.codigo_condominio = c.condominio_id "
			+ "AND m.codigo_unidade = c.unidade_id "
			+ "AND m.codigo_pessoa = cp.pessoa_id"
			, nativeQuery = true)
	List<PessoaEntity> findContatoPessoaMoradorDeUnidade (Integer condominioId, Integer alaId, Integer blocoId, Integer unidadeId);
	
	@Query(value = "SELECT cp.pessoa_id, cp.nome, cp.numero_celular, cp.email "
			+ "FROM condominio_composicao c "
			+ "INNER JOIN condominio_admin p "
			+ "INNER JOIN pessoa cp "
			+ "WHERE c.condominio_id = ?1 "
			+ "AND c.ala_id = ?2 "
			+ "AND c.bloco_id = ?3 "
			+ "AND c.unidade_id = ?4 "
			+ "AND p.codigo_tipo_pessoa = ?5 "
			+ "AND p.codigo_pessoa = cp.pessoa_id "
			//+ "AND c.condominio_composicao_id = p.codigo_composicao_condominio"
			, nativeQuery = true)
	PessoaEntity findContatoPessoaAdmin(Integer condominioId, Integer alaId, Integer blocoId, Integer unidadeId, Integer codigoTipoPessoa);
	
	/*@Query(value = "SELECT cp FROM CondominioComposicaoEntity c "
			+ "INNER JOIN CondominioPessoasEntity p "
			+ "INNER JOIN PessoaEntity cp "
			+ "WHERE c.condominioId = ?1 "
			+ "AND c.alaId = ?2 "
			+ "AND c.blocoId = ?3 "
			+ "AND c.unidadeId = ?4 "
			+ "AND p.codigoPessoa = cp.pessoaId "
			+ "AND p.codigoTipoPessoa = ?5 "
			+ "AND c.condominioComposicaoId = p.codigoComposicaoCondominio", nativeQuery = true)
	PessoaEntity findContatoPessoa(Integer condominioId, Integer alaId, Integer blocoId, Integer unidadeId, Integer codigoTipoPessoa);*/
	
}
