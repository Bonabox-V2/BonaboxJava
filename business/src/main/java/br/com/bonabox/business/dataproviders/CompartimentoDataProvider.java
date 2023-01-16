package br.com.bonabox.business.dataproviders;


import br.com.bonabox.business.api.filter.DataMDC;
import br.com.bonabox.business.dataproviders.ex.DataProviderException;
import br.com.bonabox.business.domain.*;

import java.util.List;

public interface CompartimentoDataProvider {

	List<TipoCompartimentoDisponivel> getByOwner(Integer owner) throws DataProviderException;

	List<Compartimento> consultarCompartimentos(int boxId) throws DataProviderException;

	List<EstadoBox> getEstadoBoxByOwner(Integer boxId) throws DataProviderException;

	List<EstadoBox> getAllEstadoBoxByOwner(Integer boxId) throws DataProviderException;
	
	EstadoBox update(Integer boxId, Integer newEstadoAtividade) throws DataProviderException;

	CompartimentoStatusTempResponse consultarCompatimentoByIdAleatorio(String compartimentoIdAleatorio)
			throws DataProviderException;

	CompartimentoStatusTempResponse criarComIdAleatorio(
            CompartimentoStatusTempRequest compartimentoStatusTempRequest) throws DataProviderException;

	CompartimentoStatusTempResponse atualizarComIdAleatorio(
            CompartimentoStatusTempRequest compartimentoStatusTempRequest) throws DataProviderException;

	List<CompartimentoStatusTempResponse> criarComIdAleatorioLista(
            List<CompartimentoStatusTempRequest> compartimentoStatusTempRequest) throws DataProviderException;
	
	
	CompartimentoDataProvider build(DataMDC dataMDC);
}
