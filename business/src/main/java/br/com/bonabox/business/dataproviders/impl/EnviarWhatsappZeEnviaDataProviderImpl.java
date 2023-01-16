package br.com.bonabox.business.dataproviders.impl;


import br.com.bonabox.business.api.filter.DataMDC;
import br.com.bonabox.business.dataproviders.EnviarNotificacaoDataProvider;
import br.com.bonabox.business.dataproviders.ex.DataProviderException;
import br.com.bonabox.business.util.WebClientBonabox;
import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.timelimiter.TimeLimiter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

//@Component
public class EnviarWhatsappZeEnviaDataProviderImpl implements EnviarNotificacaoDataProvider {

	private final WebClientBonabox<String> webClientBonaboxZeEnvia;
	private DataMDC dataMDC;
	
	public EnviarWhatsappZeEnviaDataProviderImpl(@Qualifier("externo-notificacao") WebClient webClient,
			WebClientBonabox<String> webClientBonaboxZeEnvia,
			@Qualifier("bulkhead.sms") Bulkhead bulkhead,
			@Qualifier("timeLimiter.sms") TimeLimiter timeLimiter,
			@Qualifier("circuitBreaker.sms") CircuitBreaker circuitBreaker) {

		this.webClientBonaboxZeEnvia = webClientBonaboxZeEnvia;
		
		this.webClientBonaboxZeEnvia.build(webClient).transform(bulkhead).transform(timeLimiter).transform(circuitBreaker);
	}

	public boolean enviar(String texto, String numeroTelefone) throws DataProviderException {

		try {

			
			String objInput = "{\"from\":\"field-scarer\",\"to\":\"" + numeroTelefone
					+ "\",\"contents\":[{\"type\":\"text\",\"text\":\"" + texto + "\"}]}";

			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			headers.put("X-API-TOKEN", "OOnir6wWZpiFfWxi2WJiG9PMAioXMPjqrR0h");

			String result = webClientBonaboxZeEnvia.build(dataMDC).post(objInput, String.class, "/whatsapp/messages", headers).block();

			System.out.println(result);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataProviderException(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public boolean enviarLista(Map<String, String> map) throws DataProviderException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public EnviarNotificacaoDataProvider build(DataMDC dataMDC) {
		this.dataMDC = dataMDC;
		return this;
	}
}
