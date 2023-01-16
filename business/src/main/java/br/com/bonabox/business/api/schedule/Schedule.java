package br.com.bonabox.business.api.schedule;


import br.com.bonabox.business.dataproviders.EnviarNotificacaoDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class Schedule {

	@Value("${entrega.mensagem.admin}")
	private String[] telefonesAdmin;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Qualifier("interno-bonabox")
	@Autowired
	private WebClient webClient;

	@Qualifier("sms")
	@Autowired
	private EnviarNotificacaoDataProvider enviarNotificacaoDataProvider;
	
	private LocalDateTime nextNotification = LocalDateTime.now();

	@Scheduled(fixedDelay = 15000)
	public void scheduleFixedDelayTask() {
		try {

			int timeMinus = 120;

			logger.info("Schecule starting | {} de inatividade", timeMinus);

			LoggerMetrics resultado = webClient.get()
					.uri(u -> u.path("/logger").queryParam("timeMinus", timeMinus).build()).retrieve()
					.bodyToMono(LoggerMetrics.class).block();

			if (resultado.getCount() == 0 &&
					Duration.between(LocalDateTime.now(), nextNotification).isNegative()) {
				for (String telefone : telefonesAdmin) {
					logger.info("Enviando SMS para administrador {}", telefone);

					Duration duration = Duration.between(resultado.getLastDate(), LocalDateTime.now());

					String mensagemAdmin = String.format(
							"[AVISO] Box off-line. Box do condominio Raizes da mata não responde faz %s minuto(s)",
							duration.toMinutes());
					enviarNotificacaoDataProvider.enviar(mensagemAdmin, telefone);
					
					nextNotification = LocalDateTime.now().plusMinutes(60);
				}
			}
		} catch (Exception e) {
			logger.error("Erro ao receber métrica {}", e);
		}
	}
}

class LoggerMetrics {
	private int count;
	private LocalDateTime lastDate;

	public LoggerMetrics() {
	}

	public LoggerMetrics(int count, LocalDateTime lastDate) {
		super();
		this.count = count;
		this.lastDate = lastDate;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public LocalDateTime getLastDate() {
		return lastDate;
	}

	public void setLastDate(LocalDateTime lastDate) {
		this.lastDate = lastDate;
	}
}