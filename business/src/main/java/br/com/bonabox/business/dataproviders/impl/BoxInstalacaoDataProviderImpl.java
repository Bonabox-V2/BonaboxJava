package br.com.bonabox.business.dataproviders.impl;

import br.com.bonabox.business.dataproviders.BoxInstalacaoDataProvider;
import br.com.bonabox.business.dataproviders.data.BoxInstalacaoDataResponse;
import br.com.bonabox.business.dataproviders.ex.DataProviderException;
import br.com.bonabox.business.usecases.ex.BaseException;
import br.com.bonabox.business.util.WebClientBonabox;
import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.timelimiter.TimeLimiter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class BoxInstalacaoDataProviderImpl implements BoxInstalacaoDataProvider {

	private final WebClientBonabox<BoxInstalacaoDataResponse> webClientBonabox;

	public BoxInstalacaoDataProviderImpl(@Qualifier("interno-box") WebClient webClient,
			@Qualifier("retry.bonabox") Retry retry,
			@Qualifier("circuitBreaker.bonabox") CircuitBreaker circuitBreaker,
			@Qualifier("timeLimiter.bonabox") TimeLimiter timeLimiter,
			@Qualifier("bulkhead.bonabox") Bulkhead bulkhead, 
			WebClientBonabox<BoxInstalacaoDataResponse> webClientBonabox) {

		this.webClientBonabox = webClientBonabox
				.build(webClient)
				.transform(bulkhead)
				.transform(circuitBreaker)
				.transform(retry)
				.transform(timeLimiter);
	}

	@Override
	public BoxInstalacaoDataResponse consultarBoxInstacao(Integer boxId) throws DataProviderException {
		try {
			
			Mono<BoxInstalacaoDataResponse> mono = webClientBonabox
					.get(uriBuilder -> uriBuilder.path("/box-install/{boxid}").build(boxId),
							BoxInstalacaoDataResponse.class);
			
			return mono.block();
		
		} catch (BaseException e) {
			throw new DataProviderException(e);
		}
	}

}
