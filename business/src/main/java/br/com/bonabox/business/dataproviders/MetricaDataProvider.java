package br.com.bonabox.business.dataproviders;


import br.com.bonabox.business.dataproviders.ex.DataProviderException;
import br.com.bonabox.business.domain.webclient.MetricaDataWebClientRequest;
import br.com.bonabox.business.domain.webclient.MetricaDataWebClientResponse;

public interface MetricaDataProvider {

	MetricaDataWebClientResponse criar(MetricaDataWebClientRequest entrada) throws DataProviderException;
	
	MetricaDataWebClientResponse consultar(int entregadorId) throws DataProviderException;

}
