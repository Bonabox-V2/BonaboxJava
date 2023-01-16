package br.com.bonabox.business.dataproviders;


import br.com.bonabox.business.api.filter.DataMDC;
import br.com.bonabox.business.dataproviders.ex.DataProviderException;
import br.com.bonabox.business.domain.webclient.BlocoDataWebClient;

import java.util.List;

public interface BlocoDataProvider {

	List<BlocoDataWebClient> consultarBloco(Integer condominioId, Integer alaId) throws DataProviderException;

	BlocoDataWebClient consultarBloco(Integer blocoId) throws DataProviderException;
	
	BlocoDataProvider build(DataMDC dataMDC);
	
}
