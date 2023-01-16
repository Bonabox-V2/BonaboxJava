package br.com.bonabox.business.usecases.impl;


import br.com.bonabox.business.dataproviders.EntregaDataProvider;
import br.com.bonabox.business.dataproviders.StatusEntregaDataProvider;
import br.com.bonabox.business.dataproviders.ex.DataProviderException;
import br.com.bonabox.business.domain.StatusEntrega;
import br.com.bonabox.business.domain.webclient.StatusEntregaDataWebClient;
import br.com.bonabox.business.usecases.UpdateEntregaUseCase;
import br.com.bonabox.business.usecases.ex.EntregaUseCaseException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class UpdateEntregaUseCaseImpl implements UpdateEntregaUseCase {

	private final EntregaDataProvider entregaDataProvider;
	private final StatusEntregaDataProvider statusEntregaDataProvider;
	
	public UpdateEntregaUseCaseImpl(EntregaDataProvider entregaDataProvider, StatusEntregaDataProvider statusEntregaDataProvider) {
		this.entregaDataProvider = entregaDataProvider;
		this.statusEntregaDataProvider = statusEntregaDataProvider;
	}

	@Override
	public StatusEntrega execute(final StatusEntrega entregaInput) throws EntregaUseCaseException {

		try {

			// 1 - Recupera informações de entrega
			// EntregaDataWebClient consultarEntrega =
			// entregaDataProvider.consultar(entregaInput.getEntregaId());

			StatusEntregaDataWebClient atualStatusEntregaDataWebClient = this
					.getStatusEntregaDataWebClient(entregaInput.getEntregaId());

			StatusEntregaDataWebClient newStatusEntregaDataWebClient = this.merge(atualStatusEntregaDataWebClient,
					entregaInput);

			// 2 - Valida timestamp de criação de entrega
			// 3 - Recupera workflow_entrega
			// 4 - Valida dados
			// 5 - Atualiza tabela de entrega

			newStatusEntregaDataWebClient.setDataHora(LocalDateTime.now());
			newStatusEntregaDataWebClient.setStatusEntregaId(0);
			//newStatusEntregaDataWebClient.setStatusEntregaId(1); // Em andamento

			/*
			 * StatusEntregaDataWebClient statusEntregaDataWebClient = new
			 * StatusEntregaDataWebClient( entregaInput.getEntregaId(), 0,
			 * entregaInput.getSituacaoId(), entregaInput.getCodigoEntregador(),
			 * entregaInput.getDataHora(), entregaInput.getCodigoAla(),
			 * entregaInput.getCodigoBloco(), entregaInput.getCodigoUnidade(),
			 * entregaInput.getCodigoNumeroPortaBox());
			 */

			StatusEntregaDataWebClient retorno = statusEntregaDataProvider.atualizar(newStatusEntregaDataWebClient);

			return new StatusEntrega(retorno.getEntregaId(), retorno.getStatusEntregaId(), retorno.getSituacaoId(),
					retorno.getCodigoEntregador(), retorno.getDataHora(), retorno.getCodigoAla(),
					retorno.getCodigoBloco(), retorno.getCodigoUnidade(), retorno.getCodigoNumeroPortaBox(), retorno.getNomeMorador());

		} catch (DataProviderException e) {
			throw new EntregaUseCaseException(e);
		} catch (EntregaUseCaseException e) {
			throw e;
		} catch (Exception e) {
			throw new EntregaUseCaseException(e);
		}
	}

	private StatusEntregaDataWebClient getStatusEntregaDataWebClient(String entregaId) throws DataProviderException {

		List<StatusEntregaDataWebClient> statusEntregaList = statusEntregaDataProvider.consultarStatusEntrega(entregaId);

		return statusEntregaList.stream()
				.sorted(Comparator.comparing(StatusEntregaDataWebClient::getDataHora).reversed()).findFirst().get();
	}
	
	private StatusEntregaDataWebClient merge(StatusEntregaDataWebClient atualEstado, StatusEntrega newEstado) throws EntregaUseCaseException{
		
		StatusEntregaDataWebClient retorno = new StatusEntregaDataWebClient(atualEstado.getEntregaId(),
				atualEstado.getStatusEntregaId(), atualEstado.getSituacaoId(), atualEstado.getCodigoEntregador(),
				atualEstado.getDataHora(), atualEstado.getCodigoAla(), atualEstado.getCodigoBloco(),
				atualEstado.getCodigoUnidade(), atualEstado.getCodigoNumeroPortaBox(), atualEstado.getNomeMorador());
		
		if (newEstado.getCodigoEntregador() != null && newEstado.getCodigoEntregador() != 0) {
			retorno.setCodigoEntregador(newEstado.getCodigoEntregador());
		} 
		
		if(newEstado.getSituacaoId() != null && newEstado.getSituacaoId() != 0 
				//&& newEstado.getCodigoAla() != null && newEstado.getCodigoAla() != 0 &&
				//newEstado.getCodigoBloco() != null && newEstado.getCodigoBloco() != 0 &&
				//newEstado.getCodigoUnidade() != null && newEstado.getCodigoUnidade() != 0
				) {
			retorno.setSituacaoId(newEstado.getSituacaoId());
		} /*else {
			throw new EntregaUseCaseException("Antes de alterar o estado da Entrega, é necessário incluir ala, bloco e unidade!", HttpStatus.BAD_REQUEST);
		}*/
		
		if (newEstado.getCodigoAla() != null && newEstado.getCodigoAla() != 0) {
			retorno.setCodigoAla(newEstado.getCodigoAla());
		} 

		if (newEstado.getCodigoBloco() != null && newEstado.getCodigoBloco() != 0) {
			retorno.setCodigoBloco(newEstado.getCodigoBloco());
		} 

		if (newEstado.getCodigoUnidade() != null && newEstado.getCodigoUnidade() != 0) {
			retorno.setCodigoUnidade(newEstado.getCodigoUnidade());
		}
		if (newEstado.getCodigoNumeroPortaBox() != null && newEstado.getCodigoNumeroPortaBox() != 0) {
			retorno.setCodigoNumeroPortaBox(newEstado.getCodigoNumeroPortaBox());
		}
		
		if (newEstado.getNomeMorador() != null && !newEstado.getNomeMorador().trim().isEmpty()) {
			retorno.setNomeMorador(newEstado.getNomeMorador());
		}
		
		return retorno;
	}

}
