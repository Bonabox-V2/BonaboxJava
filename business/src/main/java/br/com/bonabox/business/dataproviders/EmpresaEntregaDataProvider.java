package br.com.bonabox.business.dataproviders;


import br.com.bonabox.business.api.filter.DataMDC;
import br.com.bonabox.business.dataproviders.ex.DataProviderException;
import br.com.bonabox.business.domain.webclient.EmpresaEntregaDataWebClient;

import java.util.List;

public interface EmpresaEntregaDataProvider {

	List<EmpresaEntregaDataWebClient> consultarTodos() throws DataProviderException;
	
	EmpresaEntregaDataProvider build(DataMDC dataMDC);

}
