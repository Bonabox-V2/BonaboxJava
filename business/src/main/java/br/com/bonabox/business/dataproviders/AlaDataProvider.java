package br.com.bonabox.business.dataproviders;


import br.com.bonabox.business.dataproviders.ex.DataProviderException;
import br.com.bonabox.business.domain.webclient.AlaDataWebClient;

import java.util.List;

public interface AlaDataProvider {

	List<AlaDataWebClient> consultarAla(Integer condominioId) throws DataProviderException;

}
