package br.com.bonabox.business.usecases.impl;


import br.com.bonabox.business.dataproviders.BoxDataProvider;
import br.com.bonabox.business.dataproviders.BoxInstalacaoDataProvider;
import br.com.bonabox.business.dataproviders.UnidadeDataProvider;
import br.com.bonabox.business.dataproviders.data.BoxDataResponse;
import br.com.bonabox.business.dataproviders.data.BoxInstalacaoDataResponse;
import br.com.bonabox.business.dataproviders.ex.DataProviderException;
import br.com.bonabox.business.domain.Unidade;
import br.com.bonabox.business.domain.webclient.UnidadeDataWebClient;
import br.com.bonabox.business.usecases.GetCondominioUnidadeUseCase;
import br.com.bonabox.business.usecases.ex.UnidadeUseCaseException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetCondominioUnidadeUseCaseImpl implements GetCondominioUnidadeUseCase {

	private final UnidadeDataProvider unidadeDataProvider;
	private final BoxInstalacaoDataProvider boxInstalacaoDataProvider;
	private final BoxDataProvider boxDataProvider;

	public GetCondominioUnidadeUseCaseImpl(UnidadeDataProvider unidadeDataProvider,
			BoxInstalacaoDataProvider boxInstalacaoDataProvider, BoxDataProvider boxDataProvider) {
		this.unidadeDataProvider = unidadeDataProvider;
		this.boxInstalacaoDataProvider = boxInstalacaoDataProvider;
		this.boxDataProvider = boxDataProvider;
	}

	@Override
	public List<Unidade> execute(String numeroSerialBox, Integer alaId, Integer blocoId)
			throws UnidadeUseCaseException {
		try {

			BoxDataResponse boxDataResponse = boxDataProvider.getByNumeroSerialAndTipo(numeroSerialBox, "PRINCIPAL");

			BoxInstalacaoDataResponse boxInstalacaoDataResponse = boxInstalacaoDataProvider
					.consultarBoxInstacao(boxDataResponse.getBoxId());

			List<UnidadeDataWebClient> unidadeDataWebClientResponses = unidadeDataProvider
					.consultarUnidade(boxInstalacaoDataResponse.getCodigoCondominio(), alaId, blocoId);

			return unidadeDataWebClientResponses.stream()
					.sorted(Comparator.comparing(UnidadeDataWebClient::getLabelUnidade)).map(m -> {
						return new Unidade(m.getUnidadeId(), m.getPiso(), m.getNumeroUnidade(), m.getLabelUnidade());
					}).collect(Collectors.toList());

		} catch (DataProviderException e) {
			throw new UnidadeUseCaseException(e);
		} catch (Exception e) {
			throw new UnidadeUseCaseException(e);
		}
	}

	@Override
	public Unidade execute(Integer unidadeId) throws UnidadeUseCaseException {
		try {
			UnidadeDataWebClient m = unidadeDataProvider.consultarUnidade(unidadeId);

			return new Unidade(m.getUnidadeId(), m.getPiso(), m.getNumeroUnidade(), m.getLabelUnidade());
		} catch (DataProviderException e) {
			throw new UnidadeUseCaseException(e);
		} catch (Exception e) {
			throw new UnidadeUseCaseException(e);
		}
	}
}
