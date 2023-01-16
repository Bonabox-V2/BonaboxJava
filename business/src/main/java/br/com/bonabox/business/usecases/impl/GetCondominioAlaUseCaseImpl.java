package br.com.bonabox.business.usecases.impl;


import br.com.bonabox.business.dataproviders.AlaDataProvider;
import br.com.bonabox.business.dataproviders.BoxDataProvider;
import br.com.bonabox.business.dataproviders.BoxInstalacaoDataProvider;
import br.com.bonabox.business.dataproviders.data.BoxDataResponse;
import br.com.bonabox.business.dataproviders.data.BoxInstalacaoDataResponse;
import br.com.bonabox.business.dataproviders.ex.DataProviderException;
import br.com.bonabox.business.domain.Ala;
import br.com.bonabox.business.domain.webclient.AlaDataWebClient;
import br.com.bonabox.business.usecases.GetCondominioAlaUseCase;
import br.com.bonabox.business.usecases.ex.AlaUseCaseException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetCondominioAlaUseCaseImpl implements GetCondominioAlaUseCase {

	private final AlaDataProvider alaDataProvider;
	private final BoxInstalacaoDataProvider boxInstalacaoDataProvider;
	private final BoxDataProvider boxDataProvider;

	public GetCondominioAlaUseCaseImpl(AlaDataProvider alaDataProvider,
			BoxInstalacaoDataProvider boxInstalacaoDataProvider, BoxDataProvider boxDataProvider) {
		this.alaDataProvider = alaDataProvider;
		this.boxInstalacaoDataProvider = boxInstalacaoDataProvider;
		this.boxDataProvider = boxDataProvider;
	}

	@Override
	public List<Ala> execute(String numeroSerialBox) throws AlaUseCaseException {
		try {

			BoxDataResponse boxDataResponse = boxDataProvider.getByNumeroSerialAndTipo(numeroSerialBox, "PRINCIPAL");

			BoxInstalacaoDataResponse boxInstalacaoDataResponse = boxInstalacaoDataProvider
					.consultarBoxInstacao(boxDataResponse.getBoxId());

			List<AlaDataWebClient> alaDataWebClientResponses = alaDataProvider
					.consultarAla(boxInstalacaoDataResponse.getCodigoCondominio());

			return alaDataWebClientResponses.stream().sorted(Comparator.comparing(AlaDataWebClient::getLabel))
					.map(m -> {
						return new Ala(m.getAlaId(), m.getLabel(), m.getNome(), m.getDescricao());
					}).collect(Collectors.toList());

		} catch (DataProviderException e) {
			throw new AlaUseCaseException(e);
		} catch (Exception e) {
			throw new AlaUseCaseException(e);
		}
	}
}
