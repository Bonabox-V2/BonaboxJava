package br.com.bonabox.business.usecases.impl;


import br.com.bonabox.business.dataproviders.BoxDataProvider;
import br.com.bonabox.business.dataproviders.EntregadorDataProvider;
import br.com.bonabox.business.dataproviders.data.BoxDataResponse;
import br.com.bonabox.business.dataproviders.ex.DataProviderException;
import br.com.bonabox.business.domain.Entregador;
import br.com.bonabox.business.domain.webclient.EntregadorDataWebClient;
import br.com.bonabox.business.domain.webclient.EntregadorDataWebClientResponse;
import br.com.bonabox.business.usecases.CreateEntregadorUseCase;
import br.com.bonabox.business.usecases.ex.EntregadorUseCaseException;
import org.springframework.stereotype.Service;

@Service
public class CreateEntregadorUseCaseImpl implements CreateEntregadorUseCase {

	private final EntregadorDataProvider entregadorDataProvider;
	private final BoxDataProvider boxDataProvider;

	public CreateEntregadorUseCaseImpl(EntregadorDataProvider entregadorDataProvider, BoxDataProvider boxDataProvider) {
		this.entregadorDataProvider = entregadorDataProvider;
		this.boxDataProvider = boxDataProvider;
	}

	@Override
	public Entregador execute(Entregador entregador) throws EntregadorUseCaseException {
		try {

			// Recupera código do box através de número serial
			BoxDataResponse box = boxDataProvider.getByNumeroSerialAndTipo(entregador.getNumeroSerial(), "PRINCIPAL");
			
			EntregadorDataWebClientResponse entregadorResponse = entregadorDataProvider
					.criar(new EntregadorDataWebClient(entregador.getEntregadorId(), entregador.getDdi(),
							entregador.getDdd(), entregador.getTelefone(), entregador.getNome(), entregador.getDataHoraCadastro(), box.getBoxId()));

			return new Entregador(entregadorResponse.getEntregadorId(),
					entregadorResponse.getDdi(), entregadorResponse.getDdd(),
					entregadorResponse.getTelefone(), entregadorResponse.getNome(),
					entregadorResponse.getDataHoraCadastro(), entregadorResponse.getNumeroSerial());
			
		} catch (DataProviderException e) {
			throw new EntregadorUseCaseException(e);
		} catch (Exception e) {
			throw new EntregadorUseCaseException(e);
		} finally {

		}
	}
}
