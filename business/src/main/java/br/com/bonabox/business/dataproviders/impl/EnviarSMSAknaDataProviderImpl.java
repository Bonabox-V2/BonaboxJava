package br.com.bonabox.business.dataproviders.impl;


import br.com.bonabox.business.api.filter.DataMDC;
import br.com.bonabox.business.config.ConfigurationType;
import br.com.bonabox.business.dataproviders.EnviarNotificacaoDataProvider;
import br.com.bonabox.business.dataproviders.ex.DataProviderException;
import br.com.bonabox.business.util.WebClientBonabox;
import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.timelimiter.TimeLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.BodyInserters.FormInserter;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component("sms")
public class EnviarSMSAknaDataProviderImpl implements EnviarNotificacaoDataProvider {

	private final WebClientBonabox<String> webClientBonaboxAkna;

	@Autowired
	private ConfigurationType config;

	private DataMDC dataMDC;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public EnviarSMSAknaDataProviderImpl(@Qualifier("externo-notificacao-akna") WebClient webClient,
			WebClientBonabox<String> webClientBonaboxAkna, @Qualifier("bulkhead.sms") Bulkhead bulkhead,
			@Qualifier("timeLimiter.sms") TimeLimiter timeLimiter,
			@Qualifier("circuitBreaker.sms") CircuitBreaker circuitBreaker) {

		this.webClientBonaboxAkna = webClientBonaboxAkna.build(webClient).transform(bulkhead).transform(timeLimiter)
				.transform(circuitBreaker);
	}

	//@Async
	@Override
	public boolean enviar(String texto, String numeroTelefone) throws DataProviderException {

		try {

			// ##### Temporário
			if (numeroTelefone.startsWith("55")) {
				numeroTelefone = numeroTelefone.substring(2);
			}

			if (numeroTelefone == null || numeroTelefone.trim().isEmpty() || numeroTelefone.length() < 9) {
				return false;
			}

			if (!numeroTelefone.substring(2).startsWith("9")) {
				logger.error("Telefone {} inválido", numeroTelefone);
				return false;
			}

			logger.info("Iniciando envio de SMS para {}", numeroTelefone);

			String mensagem = texto;
			StringBuilder builderXml = new StringBuilder();
			builderXml.append(
					"<main><emkt trans=\"40.01\"> <remetente></remetente> <identificador></identificador> <encurtar_url>S</encurtar_url> <sms> <telefone>");
			builderXml.append(numeroTelefone);
			builderXml.append("</telefone> <mensagem>");
			builderXml.append(mensagem);
			builderXml.append("</mensagem> </sms> </emkt> </main>");

			FormInserter<String> form = BodyInserters.fromFormData("User", config.getNotification().get("User"))
					.with("Pass", config.getNotification().get("Pass"))
					.with("Client", config.getNotification().get("Client")).with("XML", builderXml.toString());

			String result = webClientBonaboxAkna.build(dataMDC).post(String.class, form).block();

			logger.info("Resultado envio de SMS: {}", result);

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DataProviderException(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//@Async
	@Override
	public boolean enviarLista(Map<String, String> map) throws DataProviderException {
		try {
			
			// String mensagem = texto;

			StringBuilder builderXml = new StringBuilder();
			builderXml.append(
					"<main><emkt trans=\"40.01\"> <remetente></remetente> <identificador></identificador> <encurtar_url>S</encurtar_url>");

			for (Map.Entry<String, String> entry : map.entrySet()) {
				String numeroTelefone = entry.getKey();
				// ##### Temporário
				if (numeroTelefone.startsWith("55")) {
					numeroTelefone = numeroTelefone.substring(2);
				}

				if (numeroTelefone == null || numeroTelefone.trim().isEmpty() || numeroTelefone.length() < 9) {
					continue;
				}

				if (!numeroTelefone.substring(2).startsWith("9")) {
					logger.error("Telefone {} inválido", numeroTelefone);
					continue;
				}

				logger.info("Iniciando envio de SMS para {}", numeroTelefone);
				
				builderXml.append("<sms> <telefone>");
				builderXml.append(numeroTelefone);
				builderXml.append("</telefone> <mensagem>");
				builderXml.append(entry.getValue());
				builderXml.append("</mensagem> </sms>");
			}

			builderXml.append("</emkt> </main>");

			FormInserter<String> form = BodyInserters.fromFormData("User", config.getNotification().get("User"))
					.with("Pass", config.getNotification().get("Pass"))
					.with("Client", config.getNotification().get("Client")).with("XML", builderXml.toString());

			String result = webClientBonaboxAkna.build(dataMDC).post(String.class, form).block();

			logger.info("Resultado envio de SMS: {}", result);

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DataProviderException(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public EnviarNotificacaoDataProvider build(DataMDC dataMDC) {
		this.dataMDC = dataMDC;
		return this;
	}

}
