package br.com.bonabox.business.dataproviders.impl;


import br.com.bonabox.business.api.filter.DataMDC;
import br.com.bonabox.business.dataproviders.BlocoDataProvider;
import br.com.bonabox.business.dataproviders.ex.DataProviderException;
import br.com.bonabox.business.domain.webclient.BlocoDataWebClient;
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
public class BlocoDataProviderImpl implements BlocoDataProvider {

	private final WebClientBonabox<BlocoDataWebClient[]> webClientInternoCondominioArray;
	private final WebClientBonabox<BlocoDataWebClient> webClientInternoCondominio;

	@Value("${interno.condominio.resource.bloco}")
	private String blocoResource;
	
	private DataMDC dataMDC;

	public BlocoDataProviderImpl(@Qualifier("interno-condominio") WebClient webClient,
			WebClientBonabox<BlocoDataWebClient[]> webClientInternoCondominioArray,
			@Qualifier("circuitBreaker.bonabox") CircuitBreaker circuitBreaker,
			WebClientBonabox<BlocoDataWebClient> webClientInternoCondominio,
			@Qualifier("bulkhead.bonabox") Bulkhead bulkhead,
			@Qualifier("timeLimiter.bonabox") TimeLimiter timeLimiter) {
		
		this.webClientInternoCondominio = webClientInternoCondominio.build(webClient).transform(circuitBreaker).transform(bulkhead).transform(timeLimiter);
        this.webClientInternoCondominioArray = webClientInternoCondominioArray.build(webClient).transform(circuitBreaker).transform(bulkhead).transform(timeLimiter);
    }

	@Override
	public List<BlocoDataWebClient> consultarBloco(Integer condominioId, Integer alaId) throws DataProviderException {

		try {
			
			Mono<BlocoDataWebClient[]> mono = webClientInternoCondominioArray.build(dataMDC).get(uriBuilder -> uriBuilder
					.path(blocoResource)
					.queryParam("condominio_id", condominioId)
					.queryParam("ala_id", alaId)
					.build(condominioId), BlocoDataWebClient[].class);

			BlocoDataWebClient[] array = mono.block();

			return Arrays.asList(array);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataProviderException(e);
		}
	}

	@Override
	public BlocoDataWebClient consultarBloco(Integer blocoId) throws DataProviderException {
		try {

			Mono<BlocoDataWebClient> mono = webClientInternoCondominio.build((WebClient) dataMDC).get(uriBuilder -> uriBuilder
					.path(blocoResource+"/get-bloco")
					.queryParam("bloco_id", blocoId)
					.build(), BlocoDataWebClient.class);
			
			/*Mono<BlocoDataWebClient> mono = webClientInternoCondominio.get().uri(uriBuilder -> uriBuilder
					.path(blocoResource+"/get-bloco")
					.queryParam("bloco_id", blocoId)
					.build()).retrieve()
					.bodyToMono(BlocoDataWebClient.class);*/

			return mono.block();

		} catch (Exception e) {
			throw new DataProviderException(e);
		}
	}

	@Override
	public BlocoDataProvider build(DataMDC dataMDC) {
		this.dataMDC = dataMDC;
		return this;
	}

}
