package br.com.bonabox.business.util;

import br.com.bonabox.business.api.filter.DataMDC;
import br.com.bonabox.business.api.filter.MDC;
import br.com.bonabox.business.usecases.ex.BaseException;
import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.reactor.bulkhead.operator.BulkheadOperator;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import io.github.resilience4j.reactor.retry.RetryOperator;
import io.github.resilience4j.reactor.timelimiter.TimeLimiterOperator;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.timelimiter.TimeLimiter;
import org.joda.time.Instant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters.FormInserter;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Map;
import java.util.function.Function;

@Component
@Scope("prototype")
public class WebClientBonabox<T> {

	private Retry retry;
	private CircuitBreaker circuitBreaker;
	private TimeLimiter timeLimiter;
	private Bulkhead bulkhead;

	private WebClient webClient;
	private DataMDC dataMDC;

	@Value("${spring.application.name}")
	private String applicarionName;

	public WebClientBonabox(Retry retry, CircuitBreaker circuitBreaker, TimeLimiter timeLimiter, Bulkhead bulkhead) {
		this.retry = retry;
		this.circuitBreaker = circuitBreaker;
		this.timeLimiter = timeLimiter;
		this.bulkhead = bulkhead;
	}

	public Mono<T> get(String uri, Class<T> typeReference) throws BaseException {

		try {

			DataMDC mdc =  this.getDataMDC();

			return webClient.get().uri(uri).headers(h -> {
				h.add("correlation-id", mdc.getCorrelationId());
				h.add("call-origem", mdc.getCallOrigem());
				h.add("user-agent", applicarionName);
				h.add("remote-start-time", Instant.now().toString());
			}).retrieve().bodyToMono(typeReference).transform(CircuitBreakerOperator.of(circuitBreaker))
					.transform(RetryOperator.of(retry)).transform(BulkheadOperator.of(bulkhead))
					.transform(TimeLimiterOperator.of(timeLimiter));

		} catch (WebClientResponseException.NotFound e) {
			throw new BaseException(e);
		} catch (WebClientResponseException e) {
			throw new BaseException(e);
		} catch (CallNotPermittedException e) {
			throw new BaseException(e, HttpStatus.SERVICE_UNAVAILABLE);
		} catch (Exception e) {
			throw new BaseException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public Mono<T> get(Function<UriBuilder, URI> uriFunction, Class<T> typeReference) throws BaseException {

		try {

			DataMDC mdc =  this.getDataMDC();
			
			return webClient.get().uri(uriFunction).headers(h -> {
				h.add("correlation-id", mdc.getCorrelationId());
				h.add("call-origem", mdc.getCallOrigem());
				h.add("user-agent", applicarionName);
				h.add("remote-start-time", Instant.now().toString());
			}).retrieve().bodyToMono(typeReference).transform(CircuitBreakerOperator.of(circuitBreaker))
					.transform(RetryOperator.of(retry)).transform(BulkheadOperator.of(bulkhead))
					.transform(TimeLimiterOperator.of(timeLimiter));

		} catch (WebClientResponseException.NotFound e) {
			throw new BaseException(e);
		} catch (WebClientResponseException e) {
			throw new BaseException(e);
		} catch (CallNotPermittedException e) {
			throw new BaseException(e, HttpStatus.SERVICE_UNAVAILABLE);
		} catch (Exception e) {
			throw new BaseException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public Mono<T> post(Function<UriBuilder, URI> uriFunction, ParameterizedTypeReference<T> typeReference)
			throws BaseException {

		try {

			DataMDC mdc = this.getDataMDC();

			return webClient.post().uri(uriFunction).headers(h -> {
				h.add("correlation-id", mdc.getCorrelationId());
				h.add("call-origem", mdc.getCallOrigem());
				h.add("user-agent", applicarionName);
				h.add("remote-start-time", Instant.now().toString());
			}).retrieve().bodyToMono(typeReference).transform(CircuitBreakerOperator.of(circuitBreaker))
					.transform(RetryOperator.of(retry)).transform(BulkheadOperator.of(bulkhead))
					.transform(TimeLimiterOperator.of(timeLimiter));

		} catch (WebClientResponseException.NotFound e) {
			throw new BaseException(e);
		} catch (WebClientResponseException e) {
			throw new BaseException(e);
		} catch (CallNotPermittedException e) {
			throw new BaseException(e, HttpStatus.SERVICE_UNAVAILABLE);
		} catch (Exception e) {
			throw new BaseException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public Mono<T> post(Object objInput, Class<T> typeReferenceResponse, String uri) throws BaseException {

		try {

			DataMDC mdc = this.getDataMDC();

			return webClient.post().uri(uri).headers(h -> {
				h.add("correlation-id", mdc.getCorrelationId());
				h.add("call-origem", mdc.getCallOrigem());
				h.add("user-agent", applicarionName);
				h.add("remote-start-time", Instant.now().toString());
			}).body(Mono.just(objInput), objInput.getClass()).retrieve().bodyToMono(typeReferenceResponse)
					.transform(CircuitBreakerOperator.of(circuitBreaker)).transform(RetryOperator.of(retry))
					.transform(BulkheadOperator.of(bulkhead)).transform(TimeLimiterOperator.of(timeLimiter));

		} catch (WebClientResponseException.NotFound e) {
			throw new BaseException(e);
		} catch (WebClientResponseException e) {
			throw new BaseException(e);
		} catch (CallNotPermittedException e) {
			throw new BaseException(e, HttpStatus.SERVICE_UNAVAILABLE);
		} catch (Exception e) {
			throw new BaseException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public Mono<T> post(Class<T> typeReferenceResponse, FormInserter<String> form)
			throws BaseException {

		try {
			
			return webClient.post().body(form)
					.retrieve()
					.bodyToMono(typeReferenceResponse)
					.transform(CircuitBreakerOperator.of(circuitBreaker))
					.transform(RetryOperator.of(retry))
					.transform(BulkheadOperator.of(bulkhead))
					.transform(TimeLimiterOperator.of(timeLimiter));

		} catch (WebClientResponseException.NotFound e) {
			throw new BaseException(e);
		} catch (WebClientResponseException e) {
			throw new BaseException(e);
		} catch (CallNotPermittedException e) {
			throw new BaseException(e, HttpStatus.SERVICE_UNAVAILABLE);
		} catch (Exception e) {
			throw new BaseException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public Mono<T> post(Object objInput, Class<T> typeReferenceResponse, String uri, Map<String, String> headers)
			throws BaseException {

		try {

			return webClient.post().uri(uri).headers(h -> {
				headers.entrySet().forEach(c -> {
					h.add(c.getKey(), c.getValue());
				});
			}).body(Mono.just(objInput), objInput.getClass()).retrieve().bodyToMono(typeReferenceResponse)
					.transform(CircuitBreakerOperator.of(circuitBreaker)).transform(RetryOperator.of(retry))
					.transform(BulkheadOperator.of(bulkhead)).transform(TimeLimiterOperator.of(timeLimiter));

		} catch (WebClientResponseException.NotFound e) {
			throw new BaseException(e);
		} catch (WebClientResponseException e) {
			throw new BaseException(e);
		} catch (CallNotPermittedException e) {
			throw new BaseException(e, HttpStatus.SERVICE_UNAVAILABLE);
		} catch (Exception e) {
			throw new BaseException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public Mono<T> put(Object objInput, Class<T> typeReferenceResponse, String uri) throws BaseException {

		try {

			DataMDC mdc = this.getDataMDC();

			return webClient.put().uri(uri).headers(h -> {
				h.add("correlation-id", mdc.getCorrelationId());
				h.add("call-origem", mdc.getCallOrigem());
				h.add("user-agent", applicarionName);
				h.add("remote-start-time", Instant.now().toString());
			}).body(Mono.just(objInput), objInput.getClass()).retrieve().bodyToMono(typeReferenceResponse)
					.transform(CircuitBreakerOperator.of(circuitBreaker)).transform(RetryOperator.of(retry))
					.transform(BulkheadOperator.of(bulkhead)).transform(TimeLimiterOperator.of(timeLimiter));

		} catch (WebClientResponseException.NotFound e) {
			throw new BaseException(e);
		} catch (WebClientResponseException e) {
			throw new BaseException(e);
		} catch (CallNotPermittedException e) {
			throw new BaseException(e, HttpStatus.SERVICE_UNAVAILABLE);
		} catch (Exception e) {
			throw new BaseException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	private DataMDC getDataMDC() {
		
		DataMDC mdc = MDC.MAPS.get(Thread.currentThread().getName());
		
		if(mdc == null) {
			mdc = dataMDC;
		}
		
		return mdc;
	}
	
	public WebClientBonabox<T> build(WebClient webClient) {
		this.webClient = webClient;
		return this;
	}
	
	public WebClientBonabox<T> build(DataMDC dataMDC) {
		this.dataMDC = dataMDC;
		return this;
	}

	public WebClientBonabox<T> transform(Retry retry) {
		this.retry = retry;
		return this;
	}

	public WebClientBonabox<T> transform(CircuitBreaker circuitBreaker) {
		this.circuitBreaker = circuitBreaker;
		return this;
	}

	public WebClientBonabox<T> transform(TimeLimiter timeLimiter) {
		this.timeLimiter = timeLimiter;
		return this;
	}

	public WebClientBonabox<T> transform(Bulkhead bulkhead) {
		this.bulkhead = bulkhead;
		return this;
	}
}
