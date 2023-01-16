package br.com.bonabox.business.dataproviders;


import br.com.bonabox.business.api.filter.DataMDC;
import br.com.bonabox.business.dataproviders.ex.DataProviderException;
import br.com.bonabox.business.domain.webclient.GenerateDataWebClientRequest;
import br.com.bonabox.business.domain.webclient.GenerateDataWebClientResponse;
import br.com.bonabox.business.domain.webclient.ValidateDataWebClientResponse;

public interface ValidateDataProvider {

	// ## Temporario
    ValidateDataProvider build(DataMDC dataMdc);
	
	GenerateDataWebClientResponse generate(GenerateDataWebClientRequest entrada) throws DataProviderException;

	ValidateDataWebClientResponse validate(String numeroSerial, String codigoAcesso) throws DataProviderException;
	
	String getValidateByToken(String token) throws DataProviderException;
}
