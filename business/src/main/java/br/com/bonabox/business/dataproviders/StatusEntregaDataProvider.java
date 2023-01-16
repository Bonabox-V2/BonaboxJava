package br.com.bonabox.business.dataproviders;


import br.com.bonabox.business.dataproviders.ex.DataProviderException;
import br.com.bonabox.business.domain.webclient.StatusEntregaDataWebClient;

import java.util.List;

public interface StatusEntregaDataProvider {

	StatusEntregaDataWebClient atualizar(StatusEntregaDataWebClient entregaDataRequest) throws DataProviderException;

	boolean finalizar(StatusEntregaDataWebClient entrega) throws DataProviderException;
	
	List<StatusEntregaDataWebClient> consultarStatusEntrega(String codigoEntrega) throws DataProviderException;

	List<StatusEntregaDataWebClient> consultarStatusEntregaByCompartimentoLista(List<Integer> listaCompartimentos, Integer situacaoId) throws DataProviderException;
	
}
