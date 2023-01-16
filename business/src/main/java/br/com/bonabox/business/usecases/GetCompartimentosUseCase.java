package br.com.bonabox.business.usecases;


import br.com.bonabox.business.api.models.GetCompartimentoLabelResponse;
import br.com.bonabox.business.api.models.GetCompartimentosResponse;
import br.com.bonabox.business.api.models.GetTipoCompartimentoDisponivelResponse;
import br.com.bonabox.business.usecases.ex.CompartimentoUseCaseException;

import java.util.List;

public interface GetCompartimentosUseCase {

	List<GetTipoCompartimentoDisponivelResponse> execute(String numeroSerial, String entregaId) throws CompartimentoUseCaseException;
	
	GetCompartimentoLabelResponse execute(String numeroSerial, String entregaId, String codigo) throws CompartimentoUseCaseException;
	
	List<GetCompartimentosResponse> execute(String numeroSerial) throws CompartimentoUseCaseException;
	
	List<GetCompartimentosResponse> obterAllEstadoBoxByOwner(String numeroSerial) throws CompartimentoUseCaseException;
	
	boolean temCompartimentosLivres (String numeroSerial) throws CompartimentoUseCaseException;
}
