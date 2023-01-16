package br.com.bonabox.business.dataproviders;


import br.com.bonabox.business.dataproviders.data.BoxInstalacaoDataResponse;
import br.com.bonabox.business.dataproviders.ex.DataProviderException;

public interface BoxInstalacaoDataProvider {

	BoxInstalacaoDataResponse consultarBoxInstacao(Integer boxId) throws DataProviderException;
	
}
