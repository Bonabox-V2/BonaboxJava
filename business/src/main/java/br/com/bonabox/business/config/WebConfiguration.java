package br.com.bonabox.business.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Configuration
public class WebConfiguration {

	@Value("${externo.notificacao.server}")
	private String urlExternoNotificacao;
	
	@Value("${externo.notificacao.akna.server}")
	private String urlExternoAknaNotificacao;
	
	@Value("${interno.box.server}")
	private String urlInternoBox;

	@Value("${interno.bonabox.server}")
	private String urlInternoBonaBox;
	
	@Value("${interno.bonabox-validate.server}")
	private String urlInternoBonaBoxValidate;
	
	@Value("${interno.condominio.server}")
	private String urlInternoCondominio;
	
	@Value("${spring.application.name}")
    private String applicarionName;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Bean("externo-notificacao-akna")
	public WebClient webExternoNotificacaoAkna() {
		return WebClient.builder().filters(exchangeFilterFunctions -> {
			exchangeFilterFunctions.add(logRequest(logger, urlExternoAknaNotificacao));
			exchangeFilterFunctions.add(logResponse(logger, urlExternoAknaNotificacao));
		}).baseUrl(urlExternoAknaNotificacao).build();
	}
	
	@Bean("externo-notificacao")
	public WebClient webExternoNotificacao() {
		return WebClient.builder().filters(exchangeFilterFunctions -> {
			exchangeFilterFunctions.add(logRequest(logger, urlExternoNotificacao));
			exchangeFilterFunctions.add(logResponse(logger, urlExternoNotificacao));
		}).baseUrl(urlExternoNotificacao).build();
	}
	
	@Bean("interno-box")
	public WebClient webInternoBox() {
		return WebClient.builder().filters(exchangeFilterFunctions -> {
			exchangeFilterFunctions.add(logRequest(logger, urlInternoBox));
			exchangeFilterFunctions.add(logResponse(logger, urlInternoBox));
		}).baseUrl(urlInternoBox).build();
	}
	
	@Bean("interno-condominio")
	public WebClient webInternoCondominio() {
		return WebClient.builder().filters(exchangeFilterFunctions -> {
			exchangeFilterFunctions.add(logRequest(logger, urlInternoCondominio));
			exchangeFilterFunctions.add(logResponse(logger, urlInternoCondominio));
		}).baseUrl(urlInternoCondominio).build();
	}

	@Bean("interno-bonabox")
	public WebClient webInternoBonaBox() {
		return WebClient.builder().filters(exchangeFilterFunctions -> {
			exchangeFilterFunctions.add(logRequest(logger, urlInternoBonaBox));
			exchangeFilterFunctions.add(logResponse(logger, urlInternoBonaBox));
		}).baseUrl(urlInternoBonaBox).build();
	}
	
	@Bean("interno-bonabox-validate")
	public WebClient webInternoBonaBoxValidate() {
		return WebClient.builder().filters(exchangeFilterFunctions -> {
			exchangeFilterFunctions.add(logRequest(logger, urlInternoBonaBoxValidate));
			exchangeFilterFunctions.add(logResponse(logger, urlInternoBonaBoxValidate));
		}).baseUrl(urlInternoBonaBoxValidate).build();
	}
	

	private ExchangeFilterFunction logRequest(final Logger logger, String path) {
		return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
			try {
				
				String threadName = Thread.currentThread().getName();
				String correlationId = clientRequest.headers().getFirst("correlation-id");
				
				LogRequest logRequest = new LogRequest(correlationId, threadName,
						clientRequest.headers().getFirst("user-agent"), clientRequest.method(),
						clientRequest.url().toString());
				
				logger.info("{\"webClientRequest\": {} {}", logRequest.toString(), "}");
			} catch (Exception e) {
				logger.error("Erro ao gerar log para {} | erro {}", path, e.getLocalizedMessage());
			}
			return Mono.just(clientRequest);
		});
	}

	private ExchangeFilterFunction logResponse(final Logger logger, String path) {
		return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
			try {
				
				String threadName = Thread.currentThread().getName();
				String correlationId = clientResponse.headers().asHttpHeaders().getFirst("correlation-id");

				//String status = clientResponse.statusCode() + "";
				LogResponse logResponse = new LogResponse(correlationId, threadName, HttpStatus.valueOf(200));
				logger.info("{\"webClientResponse\": {} {}",logResponse.toString(), "}");
				
			} catch (Exception e) {
				logger.error("Erro ao gerar log para {} | erro {}", path, e.getLocalizedMessage());
			}
			return Mono.just(clientResponse);
		});
	}
}
