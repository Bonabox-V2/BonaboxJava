package br.com.bonabox.business.dataproviders;


import br.com.bonabox.business.api.filter.DataMDC;
import br.com.bonabox.business.dataproviders.data.BoxDataResponse;
import br.com.bonabox.business.dataproviders.data.BoxInstalacaoDataResponse;
import br.com.bonabox.business.dataproviders.ex.DataProviderException;

public interface BoxDataProvider {

	//Recupera o objeto box através de número serial
    BoxDataResponse getByNumeroSerial(String numeroSerial) throws DataProviderException;
	
	BoxDataResponse getByNumeroSerialAndTipo(String numeroSerial, String tipo) throws DataProviderException;
	
	BoxInstalacaoDataResponse getBoxInstalacao(Integer boxId) throws DataProviderException;
	
	BoxDataResponse getBox(Integer boxId) throws DataProviderException;
	
	BoxDataProvider build(DataMDC dataMDC);
	
}
