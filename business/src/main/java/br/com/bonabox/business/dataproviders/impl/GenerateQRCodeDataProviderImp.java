package br.com.bonabox.business.dataproviders.impl;


import br.com.bonabox.business.api.filter.DataMDC;
import br.com.bonabox.business.dataproviders.GenerateQRCodeDataProvider;
import br.com.bonabox.business.util.GenerateQRCode;
import br.com.bonabox.business.util.WebClientBonabox;
import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.timelimiter.TimeLimiter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

@Component
public class GenerateQRCodeDataProviderImp implements GenerateQRCodeDataProvider {

	private final WebClientBonabox<Notificacao> webClientBonabox;
	
	private DataMDC dataMDC;

	public GenerateQRCodeDataProviderImp(@Qualifier("interno-bonabox") WebClient webClient,
			@Qualifier("retry.bonabox") Retry retry, @Qualifier("circuitBreaker.bonabox") CircuitBreaker circuitBreaker,
			@Qualifier("timeLimiter.bonabox") TimeLimiter timeLimiter, @Qualifier("bulkhead.bonabox") Bulkhead bulkhead,
			WebClientBonabox<Notificacao> webClientBonabox) {

		this.webClientBonabox = webClientBonabox.build(webClient).transform(bulkhead).transform(circuitBreaker)
				.transform(retry).transform(timeLimiter);

	}

	@Override
	public BufferedImage generate(String codigo) throws Exception {

		BufferedImage image = GenerateQRCode.generateQRCodeImage(codigo);
		byte[] array = GenerateQRCode.toByteArray(image, "png");
		Notificacao notificacao = new Notificacao(codigo, "QRCODE", array, LocalDateTime.now());

		webClientBonabox.build(dataMDC).post(notificacao, Notificacao.class, "/notificacao").block();

		return GenerateQRCode.generateQRCodeImage(codigo);
	}

	@Override
	public BufferedImage obter(String codigo) throws Exception {
		
		Notificacao notificacao = webClientBonabox.build(dataMDC)
				.get(uriBuilder -> uriBuilder.path("/notificacao").queryParam("notificacaoId", codigo).build(), Notificacao.class).block();

		return GenerateQRCode.toBufferedImage(notificacao.getContent());
	}

	@Override
	public GenerateQRCodeDataProvider build(DataMDC dataMDC) {
		this.dataMDC = dataMDC;
		return this;
	}
}

class Notificacao {

	private String notificacaoId;
	private String type;
	private byte[] content;
	private LocalDateTime dataHora;

	public Notificacao() {
		super();
	}

	public Notificacao(String notificacaoId, String type, byte[] content, LocalDateTime dataHora) {
		super();
		this.notificacaoId = notificacaoId;
		this.type = type;
		this.content = content;
		this.dataHora = dataHora;
	}

	public String getNotificacaoId() {
		return notificacaoId;
	}

	public String getType() {
		return type;
	}

	public byte[] getContent() {
		return content;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

}
