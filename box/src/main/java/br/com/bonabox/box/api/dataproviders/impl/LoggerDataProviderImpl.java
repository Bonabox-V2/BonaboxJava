package br.com.bonabox.box.api.dataproviders.impl;

import br.com.bonabox.box.api.dataproviders.LoggerGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class LoggerDataProviderImpl implements LoggerGateway {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${bonabox-interno.server}")
	private String urlBonaboxInternoServer;

	public LoggerDataProviderImpl() {
		super();
	}

	@Override
	public void sendLogger(String inputLogger) {
		try {
			WebClient.builder().baseUrl(urlBonaboxInternoServer).build().post().uri("/interno-bonabox/api/v1/logger").header("Content-Type", "application/json").body(Mono.just(inputLogger), inputLogger.getClass()).retrieve()
					.bodyToMono(Void.class).block();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

}