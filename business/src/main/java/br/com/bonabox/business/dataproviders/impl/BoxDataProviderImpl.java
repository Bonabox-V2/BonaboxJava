package br.com.bonabox.business.dataproviders.impl;

import br.com.bonabox.business.api.filter.DataMDC;
import br.com.bonabox.business.dataproviders.BoxDataProvider;
import br.com.bonabox.business.dataproviders.data.BoxDataResponse;
import br.com.bonabox.business.dataproviders.data.BoxInstalacaoDataResponse;
import br.com.bonabox.business.dataproviders.ex.DataProviderException;
import br.com.bonabox.business.usecases.ex.BaseException;
import br.com.bonabox.business.util.WebClientBonabox;
import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.timelimiter.TimeLimiter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class BoxDataProviderImpl implements BoxDataProvider {

	private final WebClientBonabox<BoxDataResponse> webClientBox;
	private final WebClientBonabox<BoxInstalacaoDataResponse> webClientBoxInstalacao;

	private DataMDC dataMDC;
	
	public BoxDataProviderImpl(@Qualifier("interno-box") WebClient webClient,
			WebClientBonabox<BoxDataResponse> webClientBox,
			WebClientBonabox<BoxInstalacaoDataResponse> webClientBoxInstalacao,
			@Qualifier("circuitBreaker.bonabox") CircuitBreaker circuitBreaker,
			@Qualifier("bulkhead.bonabox") Bulkhead bulkhead,
			@Qualifier("timeLimiter.bonabox") TimeLimiter timeLimiter) {
		this.webClientBox = webClientBox;
		this.webClientBoxInstalacao = webClientBoxInstalacao;

		this.webClientBox.build(webClient).transform(circuitBreaker).transform(bulkhead).transform(timeLimiter);
		this.webClientBoxInstalacao.build(webClient).transform(circuitBreaker).transform(bulkhead).transform(timeLimiter);
	}

	@Override
	public BoxDataResponse getByNumeroSerial(String numeroSerial) throws DataProviderException {
		try {

			Mono<BoxDataResponse> mono = webClientBox.build(dataMDC).get(
					uriBuilder -> uriBuilder.path("/box/{numero_serial}").build(numeroSerial), BoxDataResponse.class);

			return mono.block();

		} catch (BaseException e) {
			throw new DataProviderException(e);
		}
	}

	@Override
	public BoxDataResponse getByNumeroSerialAndTipo(String numeroSerial, String tipo) throws DataProviderException {
		try {

			Mono<BoxDataResponse> mono = webClientBox.build(dataMDC).get(
					uriBuilder -> uriBuilder.path("/box/{numero_serial}/{tipo}").build(numeroSerial, tipo),
					BoxDataResponse.class);

			return mono.block();

		} catch (BaseException e) {
			throw new DataProviderException(e);
		}
	}

	@Override
	public BoxInstalacaoDataResponse getBoxInstalacao(Integer boxId) throws DataProviderException {
		try {

			Mono<BoxInstalacaoDataResponse> mono = webClientBoxInstalacao.build(dataMDC).get(
					uriBuilder -> uriBuilder.path("/box-install/{box_id}").build(boxId),
					BoxInstalacaoDataResponse.class);

			return mono.block();

		} catch (BaseException e) {
			throw new DataProviderException(e);
		}
	}

	@Override
	public BoxDataResponse getBox(Integer boxId) throws DataProviderException {
		try {

			Mono<BoxDataResponse> mono = webClientBox.build(dataMDC).get(
					uriBuilder -> uriBuilder.path("/box/{box_id}").build(boxId),
					BoxDataResponse.class);

			return mono.block();

		} catch (BaseException e) {
			throw new DataProviderException(e);
		}
	}

	@Override
	public BoxDataProvider build(DataMDC dataMDC) {
		this.dataMDC = dataMDC;
		return this;
	}
}