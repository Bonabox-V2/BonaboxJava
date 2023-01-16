package br.com.bonabox.condominio.api.usecase;



import br.com.bonabox.condominio.api.domain.Pessoa;

import java.util.List;

public interface PessoaUseCase {

	public Pessoa obterPessoaPorUnidade(Integer condominioId, Integer alaId, Integer blocoId, Integer unidadeId,
										Integer codigoTipoPessoa);

	public Pessoa obterPessoaMoradorPrincipalPorUnidade(Integer condominioId, Integer alaId, Integer blocoId,
			Integer unidadeId);
	
	public List<Pessoa> obterPessoaMoradorPorUnidadeList(Integer condominioId, Integer alaId, Integer blocoId,
			Integer unidadeId);
}
