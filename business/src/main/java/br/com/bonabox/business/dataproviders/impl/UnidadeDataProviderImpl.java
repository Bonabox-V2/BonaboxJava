package br.com.bonabox.business.dataproviders.impl;


import br.com.bonabox.business.api.filter.DataMDC;
import br.com.bonabox.business.dataproviders.UnidadeDataProvider;
import br.com.bonabox.business.dataproviders.ex.DataProviderException;
import br.com.bonabox.business.domain.webclient.UnidadeDataWebClient;
import br.com.bonabox.business.usecases.ex.BaseException;
import br.com.bonabox.business.util.WebClientBonabox;
import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.timelimiter.TimeLimiter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
public class UnidadeDataProviderImpl implements UnidadeDataProvider {

	private final WebClientBonabox<UnidadeDataWebClient[]> webClientBonabox;
	private final WebClientBonabox<UnidadeDataWebClient> webClientBonaboxUnidade;

	@Value("${interno.condominio.resource.unidade}")
	private String unidadeResource;
	
	private DataMDC dataMDC;

	public UnidadeDataProviderImpl(@Qualifier("interno-condominio") WebClient webClient,
			WebClientBonabox<UnidadeDataWebClient[]> webClientBonabox,
			WebClientBonabox<UnidadeDataWebClient> webClientBonaboxUnidade,
			@Qualifier("circuitBreaker.bonabox") CircuitBreaker circuitBreaker,
			@Qualifier("bulkhead.bonabox") Bulkhead bulkhead,
			@Qualifier("timeLimiter.bonabox") TimeLimiter timeLimiter) {
		this.webClientBonabox = webClientBonabox.build(webClient).transform(bulkhead).transform(circuitBreaker).transform(timeLimiter);
		this.webClientBonaboxUnidade = webClientBonaboxUnidade.build(webClient).transform(bulkhead).transform(circuitBreaker).transform(timeLimiter);
	}

	@Override
	public List<UnidadeDataWebClient> consultarUnidade(Integer condominioId, Integer alaId, Integer blocoId)
			throws DataProviderException {

		try {

			Mono<UnidadeDataWebClient[]> mono = webClientBonabox.build(dataMDC).get(
					uriBuilder -> uriBuilder.path(unidadeResource).queryParam("condominio_id", condominioId)
							.queryParam("ala_id", alaId).queryParam("bloco_id", blocoId).build(condominioId),
					UnidadeDataWebClient[].class);

			UnidadeDataWebClient[] array = mono.block();

			return Arrays.asList(array);

		} catch (BaseException e) {
			throw new DataProviderException(e);
		}
	}

	@Override
	public UnidadeDataWebClient consultarUnidade(Integer unidadeId) throws DataProviderException {
		try {

			Mono<UnidadeDataWebClient> mono = webClientBonaboxUnidade.build(dataMDC).get(uriBuilder -> uriBuilder
					.path(unidadeResource + "/get-unidade").queryParam("unidade_id", unidadeId).build(),
					UnidadeDataWebClient.class);

			return mono.block();

		} catch (BaseException e) {
			throw new DataProviderException(e);
		}
	}

	@Override
	public UnidadeDataProvider build(DataMDC dataMDC) {
		this.dataMDC = dataMDC;
		return this;
	}

}
