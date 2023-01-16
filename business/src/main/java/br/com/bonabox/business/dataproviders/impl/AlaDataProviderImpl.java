package br.com.bonabox.business.dataproviders.impl;

import br.com.bonabox.business.dataproviders.AlaDataProvider;
import br.com.bonabox.business.dataproviders.ex.DataProviderException;
import br.com.bonabox.business.domain.webclient.AlaDataWebClient;
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
public class AlaDataProviderImpl implements AlaDataProvider {

	private final WebClientBonabox<AlaDataWebClient[]> webClientInternoCondominioArray;

	@Value("${interno.condominio.resource.ala}")
	private String alaResource;

	public AlaDataProviderImpl(@Qualifier("interno-condominio") WebClient webClient,
			WebClientBonabox<AlaDataWebClient[]> webClientInternoCondominioArray,
			@Qualifier("circuitBreaker.bonabox") CircuitBreaker circuitBreaker,
			@Qualifier("bulkhead.bonabox") Bulkhead bulkhead,
			@Qualifier("timeLimiter.bonabox") TimeLimiter timeLimiter) {

		this.webClientInternoCondominioArray = webClientInternoCondominioArray.build(webClient).transform(circuitBreaker).transform(bulkhead).transform(timeLimiter);
	}

	@Override
	public List<AlaDataWebClient> consultarAla(Integer condominioId) throws DataProviderException {

		try {

			Mono<AlaDataWebClient[]> mono = webClientInternoCondominioArray.get(uriBuilder -> uriBuilder.path(alaResource)
					.queryParam("condominio_id", condominioId).build(condominioId), AlaDataWebClient[].class);

			AlaDataWebClient[] array = mono.block();

			return Arrays.asList(array);
		} catch (Exception e) {
			throw new DataProviderException(e);
		}
	}

}
