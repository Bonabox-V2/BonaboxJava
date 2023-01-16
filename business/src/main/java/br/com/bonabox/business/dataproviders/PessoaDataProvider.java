package br.com.bonabox.business.dataproviders;


import br.com.bonabox.business.api.filter.DataMDC;
import br.com.bonabox.business.dataproviders.ex.DataProviderException;
import br.com.bonabox.business.domain.webclient.PessoaDataWebClient;

import java.util.List;

public interface PessoaDataProvider {

	PessoaDataWebClient consultarPessoa(Integer condominioId, Integer alaId, Integer blocoId, Integer unidadeId,
										Integer codigoTipoPessoa) throws DataProviderException;

	PessoaDataWebClient consultarPessoaMoradorPrincipalPorUnidade(Integer condominioId, Integer alaId,
                                                                  Integer blocoId, Integer unidadeId) throws DataProviderException;
	
	List<PessoaDataWebClient> consultarPessoaMoradorPrincipalPorUnidadeList(Integer condominioId, Integer alaId,
                                                                            Integer blocoId, Integer unidadeId) throws DataProviderException;
	
	PessoaDataProvider build(DataMDC dataMDC);
}
