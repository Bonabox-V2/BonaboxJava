package br.com.bonabox.business.dataproviders;


import br.com.bonabox.business.api.filter.DataMDC;
import br.com.bonabox.business.dataproviders.ex.DataProviderException;
import br.com.bonabox.business.domain.webclient.EntregadorDataWebClient;
import br.com.bonabox.business.domain.webclient.EntregadorDataWebClientResponse;

public interface EntregadorDataProvider {

	EntregadorDataWebClientResponse criar(EntregadorDataWebClient entrada) throws DataProviderException;
	
	EntregadorDataWebClientResponse consultar(int entregadorId) throws DataProviderException;
	
	EntregadorDataProvider build(DataMDC dataMDC);

}
