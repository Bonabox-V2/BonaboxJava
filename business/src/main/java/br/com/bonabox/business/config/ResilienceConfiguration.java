package br.com.bonabox.business.config;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.configure.CircuitBreakerConfigurationProperties;
import io.github.resilience4j.common.CompositeCustomizer;
import io.github.resilience4j.common.bulkhead.configuration.BulkheadConfigCustomizer;
import io.github.resilience4j.common.bulkhead.configuration.BulkheadConfigurationProperties;
import io.github.resilience4j.common.circuitbreaker.configuration.CircuitBreakerConfigCustomizer;
import io.github.resilience4j.common.retry.configuration.RetryConfigCustomizer;
import io.github.resilience4j.common.retry.configuration.RetryConfigurationProperties;
import io.github.resilience4j.common.timelimiter.configuration.TimeLimiterConfigCustomizer;
import io.github.resilience4j.common.timelimiter.configuration.TimeLimiterConfigurationProperties;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 
 * @author Leandro Marques
 *
 * This class is responsible for implement the circuit breaker, bulkhead, timelimiter and retry polices
 */
@Configuration
public class ResilienceConfiguration {

	@Bean
	public CircuitBreakerRegistry circuit(CircuitBreakerConfigurationProperties breakerConfigurationProperties) {
		CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.ofDefaults();

		breakerConfigurationProperties.getInstances().forEach((name, properties) -> {
			CircuitBreakerConfig circuitBreakerConfig = breakerConfigurationProperties.createCircuitBreakerConfig(name,
					properties, new CompositeCustomizer<CircuitBreakerConfigCustomizer>(null));
			circuitBreakerRegistry.circuitBreaker(name, circuitBreakerConfig);
		});

		return circuitBreakerRegistry;
	}

	@Bean("circuitBreaker.bonabox")
	public CircuitBreaker applicationCircuitBreakerBonabox(CircuitBreakerRegistry circuitBreakerRegistry) {
		return circuitBreakerRegistry.circuitBreaker("bonabox");
	}
	
	@Bean("circuitBreaker.box")
	public CircuitBreaker applicationCircuitBreakerBox(CircuitBreakerRegistry circuitBreakerRegistry) {
		return circuitBreakerRegistry.circuitBreaker("box");
	}
	
	@Bean("circuitBreaker.condominio")
	public CircuitBreaker applicationCircuitBreakerCondominio(CircuitBreakerRegistry circuitBreakerRegistry) {
		return circuitBreakerRegistry.circuitBreaker("condominio");
	}
	
	@Bean("circuitBreaker.validate")
	public CircuitBreaker applicationCircuitBreakerValidate(CircuitBreakerRegistry circuitBreakerRegistry) {
		return circuitBreakerRegistry.circuitBreaker("validate");
	}
	
	@Bean("circuitBreaker.sms")
	public CircuitBreaker applicationCircuitBreakerSms(CircuitBreakerRegistry circuitBreakerRegistry) {
		return circuitBreakerRegistry.circuitBreaker("sms");
	}
	
	@Bean
	@Primary
	public CircuitBreaker applicationCircuitBreakerDefault(CircuitBreakerRegistry circuitBreakerRegistry) {
		return circuitBreakerRegistry.circuitBreaker("default");
	}

	@Bean
	public RetryRegistry retry(RetryConfigurationProperties retryConfigurationProperties) {

		RetryRegistry registry = RetryRegistry.ofDefaults();

		retryConfigurationProperties.getInstances().forEach((name, properties) -> {
			RetryConfig retryConfig = retryConfigurationProperties.createRetryConfig(properties,
					new CompositeCustomizer<RetryConfigCustomizer>(null), name);
			registry.retry(name, retryConfig);
		});
		return registry;
	}

	@Bean("retry.bonabox")
	public Retry applicationRetry(RetryRegistry retryRegistry) {
		return retryRegistry.retry("bonabox");
	}
	
	@Bean("retry.validate")
	public Retry applicationRetryValidate(RetryRegistry retryRegistry) {
		return retryRegistry.retry("validate");
	}
	
	@Bean("retry.sms")
	public Retry applicationRetrySms(RetryRegistry retryRegistry) {
		return retryRegistry.retry("sms");
	}
	
	@Bean
	@Primary
	public Retry applicationRetryDefault(RetryRegistry retryRegistry) {
		return retryRegistry.retry("default");
	}

	@Bean
	public TimeLimiterRegistry limiter(TimeLimiterConfigurationProperties timeLimiterConfigurationProperties) {

		TimeLimiterRegistry registry = TimeLimiterRegistry.ofDefaults();

		timeLimiterConfigurationProperties.getInstances().forEach((name, properties) -> {
			TimeLimiterConfig timeLimiterConfig = timeLimiterConfigurationProperties.createTimeLimiterConfig(name,
					properties, new CompositeCustomizer<TimeLimiterConfigCustomizer>(null));
			registry.timeLimiter(name, timeLimiterConfig);
		});
		return registry;
	}

	@Bean("timeLimiter.bonabox")
	public TimeLimiter applicationLimiter(TimeLimiterRegistry timeLimiterRegistry) {
		return timeLimiterRegistry.timeLimiter("bonabox");
	}
	
	@Bean("timeLimiter.validate")
	public TimeLimiter applicationLimiterValidate(TimeLimiterRegistry timeLimiterRegistry) {
		return timeLimiterRegistry.timeLimiter("validate");
	}
	
	@Bean("timeLimiter.sms")
	public TimeLimiter applicationLimiterSms(TimeLimiterRegistry timeLimiterRegistry) {
		return timeLimiterRegistry.timeLimiter("sms");
	}
	
	@Bean
	@Primary
	public TimeLimiter applicationLimiterDefault(TimeLimiterRegistry timeLimiterRegistry) {
		return timeLimiterRegistry.timeLimiter("default");
	}


	@Bean
	public BulkheadRegistry bulkhead(BulkheadConfigurationProperties bulkheadConfigurationProperties) {

		BulkheadRegistry bulkhead = BulkheadRegistry.ofDefaults();

		bulkheadConfigurationProperties.getInstances().forEach((name, properties) -> {

			BulkheadConfig bulkheadConfig = bulkheadConfigurationProperties.createBulkheadConfig(properties,
					new CompositeCustomizer<BulkheadConfigCustomizer>(null), name);
			bulkhead.bulkhead(name, bulkheadConfig);
		});
		return bulkhead;
	}

	@Bean("bulkhead.bonabox")
	public Bulkhead applicationBulkhead(BulkheadRegistry bulkheadRegistry) {
		return bulkheadRegistry.bulkhead("bonabox");
	}
	
	@Bean("bulkhead.validate")
	public Bulkhead applicationBulkheadValidate(BulkheadRegistry bulkheadRegistry) {
		return bulkheadRegistry.bulkhead("validate");
	}
	
	@Bean("bulkhead.sms")
	public Bulkhead applicationBulkheadSms(BulkheadRegistry bulkheadRegistry) {
		return bulkheadRegistry.bulkhead("sms");
	}
	
	@Bean
	@Primary
	public Bulkhead applicationBulkheadDefault(BulkheadRegistry bulkheadRegistry) {
		return bulkheadRegistry.bulkhead("default");
	}

}
