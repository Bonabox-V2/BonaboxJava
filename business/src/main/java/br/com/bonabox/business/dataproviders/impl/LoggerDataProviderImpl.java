package br.com.bonabox.business.dataproviders.impl;


import br.com.bonabox.business.dataproviders.LoggerGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class LoggerDataProviderImpl implements LoggerGateway {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final WebClient webClient;

	public LoggerDataProviderImpl(@Qualifier("interno-bonabox") WebClient webClient) {
		super();
		this.webClient = webClient;
	}

	@Override
	public void sendLogger(String inputLogger) {
		try {
			webClient.post().uri("/logger").header("Content-Type", "application/json").body(Mono.just(inputLogger), inputLogger.getClass()).retrieve()
					.bodyToMono(Void.class).block();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

}