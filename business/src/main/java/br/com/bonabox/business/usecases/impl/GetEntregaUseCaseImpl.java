package br.com.bonabox.business.usecases.impl;


import br.com.bonabox.business.dataproviders.EntregaDataProvider;
import br.com.bonabox.business.dataproviders.ex.DataProviderException;
import br.com.bonabox.business.domain.Entrega;
import br.com.bonabox.business.domain.webclient.EntregaDataWebClientResponse;
import br.com.bonabox.business.usecases.GetEntregaUseCase;
import br.com.bonabox.business.usecases.ex.EntregaUseCaseException;
import org.springframework.stereotype.Service;

@Service
public class GetEntregaUseCaseImpl implements GetEntregaUseCase {

	private final EntregaDataProvider entregaDataProvider;

	public GetEntregaUseCaseImpl(EntregaDataProvider entregaDataProvider) {
		this.entregaDataProvider = entregaDataProvider;
	}

	@Override
	public Entrega execute(String codigoEntrega) throws EntregaUseCaseException {
		try {

			// Consulta entrega na tabela "entrega"
			EntregaDataWebClientResponse entrega = entregaDataProvider.consultar(codigoEntrega);
			
			return new Entrega(null, null, null, null);
		} catch (DataProviderException e) {
			throw new EntregaUseCaseException(e);
		} finally {

		}
	}
}
