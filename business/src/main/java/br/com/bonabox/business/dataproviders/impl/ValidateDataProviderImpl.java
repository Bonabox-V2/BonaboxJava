package br.com.bonabox.business.dataproviders.impl;


import br.com.bonabox.business.api.filter.DataMDC;
import br.com.bonabox.business.dataproviders.ValidateDataProvider;
import br.com.bonabox.business.dataproviders.ex.DataProviderException;
import br.com.bonabox.business.domain.webclient.GenerateDataWebClientRequest;
import br.com.bonabox.business.domain.webclient.GenerateDataWebClientResponse;
import br.com.bonabox.business.domain.webclient.ValidateDataWebClientResponse;
import br.com.bonabox.business.util.WebClientBonabox;
import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.timelimiter.TimeLimiter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Component
public class ValidateDataProviderImpl implements ValidateDataProvider {

	private final WebClientBonabox<GenerateDataWebClientResponse> webClientBonabox;
	private final WebClientBonabox<ValidateDataWebClientResponse> webClientBonaboxValidate;
	private final WebClientBonabox<String> webClientBonaboxString;
	
	private DataMDC dataMdc;

	public ValidateDataProviderImpl(@Qualifier("interno-bonabox-validate") WebClient webClient,
			WebClientBonabox<GenerateDataWebClientResponse> webClientBonabox,
			WebClientBonabox<ValidateDataWebClientResponse> webClientBonaboxValidate,
			WebClientBonabox<String> webClientBonaboxString, @Qualifier("retry.validate") Retry retry,
			@Qualifier("circuitBreaker.validate") CircuitBreaker circuitBreaker,
			@Qualifier("timeLimiter.validate") TimeLimiter timeLimiter,
			@Qualifier("bulkhead.validate") Bulkhead bulkhead) {

		this.webClientBonabox = webClientBonabox.build(webClient).transform(bulkhead).transform(circuitBreaker)
				.transform(retry).transform(timeLimiter);
		this.webClientBonaboxValidate = webClientBonaboxValidate.build(webClient).transform(bulkhead)
				.transform(circuitBreaker).transform(retry).transform(timeLimiter);
		this.webClientBonaboxString = webClientBonaboxString.build(webClient).transform(bulkhead)
				.transform(circuitBreaker).transform(retry).transform(timeLimiter);
	}

	@Override
	public GenerateDataWebClientResponse generate(GenerateDataWebClientRequest entregaDataRequest)
			throws DataProviderException {

		try {

			Mono<GenerateDataWebClientResponse> mono = webClientBonabox.build(dataMdc).post(entregaDataRequest,
					GenerateDataWebClientResponse.class, "/generate");

			return mono.block();
		} catch (Exception e) {
			throw new DataProviderException(e);
		}
	}

	@Override
	public ValidateDataWebClientResponse validate(String numeroSerial, String codigoAcesso)
			throws DataProviderException {
		try {

			Mono<ValidateDataWebClientResponse> mono = webClientBonaboxValidate.get(uriBuilder -> uriBuilder
					.path("/validate/porta/{numero-serial}/{codigo-de-acesso}").build(numeroSerial, codigoAcesso),
					ValidateDataWebClientResponse.class);

			return mono.block();
		} catch (WebClientResponseException e) {
			throw new DataProviderException(e);
		} catch (Exception e) {
			throw new DataProviderException(e);
		}
	}

	@Override
	public String getValidateByToken(String token) throws DataProviderException {
		try {

			Mono<String> mono = webClientBonaboxString.get(
					uriBuilder -> uriBuilder.path("/validate/porta").queryParam("token", token).build(), String.class);

			return mono.block();
		} catch (WebClientResponseException e) {
			throw new DataProviderException(e);
		} catch (Exception e) {
			throw new DataProviderException(e);
		}
	}

	@Override
	public ValidateDataProvider build(DataMDC dataMdc) {
		this.dataMdc = dataMdc;
		return this;
	}

}
