package br.com.bonabox.business.dataproviders;


import br.com.bonabox.business.api.filter.DataMDC;
import br.com.bonabox.business.dataproviders.ex.DataProviderException;
import br.com.bonabox.business.domain.webclient.CondominioDataWebClientRequest;
import br.com.bonabox.business.domain.webclient.CondominioEnderecoDataWebClientResponse;

public interface CondominioDataProvider {

	CondominioDataWebClientRequest consultarCondominio(Integer condominioId) throws DataProviderException;
	
	CondominioEnderecoDataWebClientResponse consultarCondominioEndereco(Integer condominioId) throws DataProviderException;

	CondominioDataProvider build(DataMDC dataMDC);
	
}
