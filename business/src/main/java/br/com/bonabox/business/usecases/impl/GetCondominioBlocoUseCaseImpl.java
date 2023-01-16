package br.com.bonabox.business.usecases.impl;


import br.com.bonabox.business.dataproviders.BlocoDataProvider;
import br.com.bonabox.business.dataproviders.BoxDataProvider;
import br.com.bonabox.business.dataproviders.BoxInstalacaoDataProvider;
import br.com.bonabox.business.dataproviders.data.BoxDataResponse;
import br.com.bonabox.business.dataproviders.data.BoxInstalacaoDataResponse;
import br.com.bonabox.business.dataproviders.ex.DataProviderException;
import br.com.bonabox.business.domain.Bloco;
import br.com.bonabox.business.domain.webclient.BlocoDataWebClient;
import br.com.bonabox.business.usecases.GetCondominioBlocoUseCase;
import br.com.bonabox.business.usecases.ex.BlocoUseCaseException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetCondominioBlocoUseCaseImpl implements GetCondominioBlocoUseCase {

	private final BlocoDataProvider blocoDataProvider;
	private final BoxInstalacaoDataProvider boxInstalacaoDataProvider;
	private final BoxDataProvider boxDataProvider;

	public GetCondominioBlocoUseCaseImpl(BlocoDataProvider blocoDataProvider,
			BoxInstalacaoDataProvider boxInstalacaoDataProvider, BoxDataProvider boxDataProvider) {
		this.blocoDataProvider = blocoDataProvider;
		this.boxInstalacaoDataProvider = boxInstalacaoDataProvider;
		this.boxDataProvider = boxDataProvider;
	}

	@Override
	public List<Bloco> execute(String numeroSerialBox, Integer alaId) throws BlocoUseCaseException {
		try {

			BoxDataResponse boxDataResponse = boxDataProvider.getByNumeroSerialAndTipo(numeroSerialBox, "PRINCIPAL");

			BoxInstalacaoDataResponse boxInstalacaoDataResponse = boxInstalacaoDataProvider
					.consultarBoxInstacao(boxDataResponse.getBoxId());

			List<BlocoDataWebClient> blocoDataWebClientResponses = blocoDataProvider
					.consultarBloco(boxInstalacaoDataResponse.getCodigoCondominio(), alaId);

			return blocoDataWebClientResponses.stream().sorted(Comparator.comparing(BlocoDataWebClient::getLabel))
					.map(m -> {
						return new Bloco(m.getBlocoId(), m.getLabel(), m.getNome(), m.getDescricao());
					}).collect(Collectors.toList());

		} catch (DataProviderException e) {
			throw new BlocoUseCaseException(e);
		} catch (Exception e) {
			throw new BlocoUseCaseException(e);
		}
	}

	@Override
	public Bloco execute(Integer blocoId) throws BlocoUseCaseException {
		try {
			BlocoDataWebClient blocoDataWebClient = blocoDataProvider.consultarBloco(blocoId);

			return new Bloco(blocoDataWebClient.getBlocoId(), blocoDataWebClient.getLabel(),
					blocoDataWebClient.getNome(), blocoDataWebClient.getDescricao());

		} catch (DataProviderException e) {
			throw new BlocoUseCaseException(e);
		} catch (Exception e) {
			throw new BlocoUseCaseException(e);
		}
	}

}
