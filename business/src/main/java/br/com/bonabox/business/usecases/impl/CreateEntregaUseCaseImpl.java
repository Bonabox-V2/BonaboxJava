package br.com.bonabox.business.usecases.impl;


import br.com.bonabox.business.dataproviders.BoxDataProvider;
import br.com.bonabox.business.dataproviders.BoxInstalacaoDataProvider;
import br.com.bonabox.business.dataproviders.EntregaDataProvider;
import br.com.bonabox.business.dataproviders.StatusEntregaDataProvider;
import br.com.bonabox.business.dataproviders.data.BoxDataResponse;
import br.com.bonabox.business.dataproviders.data.BoxInstalacaoDataResponse;
import br.com.bonabox.business.dataproviders.ex.DataProviderException;
import br.com.bonabox.business.domain.Entrega;
import br.com.bonabox.business.domain.webclient.EntregaDataWebClient;
import br.com.bonabox.business.domain.webclient.EntregaDataWebClientResponse;
import br.com.bonabox.business.domain.webclient.StatusEntregaDataWebClient;
import br.com.bonabox.business.usecases.CreateEntregaUseCase;
import br.com.bonabox.business.usecases.ex.EntregaUseCaseException;
import br.com.bonabox.business.util.GenerateCodigoEntrega;
import br.com.bonabox.business.util.GenerateUID;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreateEntregaUseCaseImpl implements CreateEntregaUseCase {

	private final String PRINCIPAL = "PRINCIPAL";
	private final EntregaDataProvider entregaDataProvider;
	private final StatusEntregaDataProvider statusEntregaDataProvider;
	private final BoxDataProvider boxDataProvider;
	private final BoxInstalacaoDataProvider boxInstalacaoDataProvider;
	private final GenerateUID generateUID;

	public CreateEntregaUseCaseImpl(EntregaDataProvider entregaDataProvider, StatusEntregaDataProvider statusEntregaDataProvider, BoxDataProvider boxDataProvider,
			BoxInstalacaoDataProvider boxInstalacaoDataProvider,
			@Qualifier("generate-uid-entrega") GenerateUID generateUID) {
		this.entregaDataProvider = entregaDataProvider;
		this.statusEntregaDataProvider = statusEntregaDataProvider;
		this.boxDataProvider = boxDataProvider;
		this.boxInstalacaoDataProvider = boxInstalacaoDataProvider;
		this.generateUID = generateUID;

		this.generateUID.build(new GenerateCodigoEntrega());
	}

	@Override
	public Entrega execute(Entrega entrega) throws EntregaUseCaseException {
		try {

			// Recupera código do box através de número serial
			BoxDataResponse box = boxDataProvider.getByNumeroSerialAndTipo(entrega.getNumeroSerial(), PRINCIPAL);

			// Verifica se existe entrega em aberto para o numeroSerial/código do box
			// informado.

			// Desabilita entregas em aberto

			// Gera novo código de entrega
			String codigoEntrega = generateUID.generatingCodigo(6);

			int SITUACAO_ENTREGA_INICIADA = 1;
			EntregaDataWebClient entregaDataRequest = new EntregaDataWebClient();
			entregaDataRequest.setEntregaId(codigoEntrega);
			entregaDataRequest.setCodigoBox(box.getBoxId());
			entregaDataRequest.setCodigoEmpresaEntregadora(entrega.getEmpresaEntregadora());
			entregaDataRequest.setCodigoSituacaoEntrega(SITUACAO_ENTREGA_INICIADA); // Aberta

			BoxInstalacaoDataResponse dataResponse = boxInstalacaoDataProvider.consultarBoxInstacao(box.getBoxId());
			
			entregaDataRequest.setCodigoCondominio(dataResponse.getCodigoCondominio());

			// Registra entrega na tabela "entrega"
			entregaDataRequest.setDataHoraCriacao(LocalDateTime.now());
			EntregaDataWebClientResponse response = entregaDataProvider.criar(entregaDataRequest);

			StatusEntregaDataWebClient statusEntregaDataWebClient = new StatusEntregaDataWebClient(
					response.getEntregaId(), 0, SITUACAO_ENTREGA_INICIADA, response.getCodigoEntregador(),
					LocalDateTime.now(), null, response.getCodigoBloco(), response.getCodigoUnidade(),
					response.getCodigoNumeroPortaBox(), null);

			// Atualiza tabela de status_entrega com a primeira iteração da 'entrega'
			statusEntregaDataProvider.atualizar(statusEntregaDataWebClient);

			// Iniciar workflow para iniciar processo de entrega

			return new Entrega(entrega.getNumeroSerial(), codigoEntrega, response.getDataHoraCriacao(),
					entrega.getEmpresaEntregadora());

		} catch (DataProviderException e) {
			throw new EntregaUseCaseException(e);
		} catch (Exception e) {
			throw new EntregaUseCaseException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {

		}
	}
}
