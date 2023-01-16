package br.com.bonabox.business.dataproviders;


import br.com.bonabox.business.api.filter.DataMDC;
import br.com.bonabox.business.dataproviders.ex.DataProviderException;
import br.com.bonabox.business.domain.webclient.EntregaDataWebClient;
import br.com.bonabox.business.domain.webclient.EntregaDataWebClientResponse;

public interface EntregaDataProvider {

	EntregaDataWebClientResponse criar(EntregaDataWebClient entrada) throws DataProviderException;

	EntregaDataWebClientResponse consultar(String codigoEntrega) throws DataProviderException;
	
	EntregaDataProvider build(DataMDC dataMDC);

}
