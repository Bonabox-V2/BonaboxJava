package br.com.bonabox.business.dataproviders;


import br.com.bonabox.business.api.filter.DataMDC;
import br.com.bonabox.business.dataproviders.ex.DataProviderException;
import br.com.bonabox.business.domain.webclient.UnidadeDataWebClient;

import java.util.List;

public interface UnidadeDataProvider {

	List<UnidadeDataWebClient> consultarUnidade(Integer condominioId, Integer alaId, Integer blocoId)
			throws DataProviderException;

	UnidadeDataWebClient consultarUnidade(Integer unidadeId) throws DataProviderException;
	
	UnidadeDataProvider build(DataMDC dataMDC);

}
