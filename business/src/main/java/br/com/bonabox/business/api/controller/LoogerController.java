package br.com.bonabox.business.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/box/api/v1/logger")
public class LoogerController {

	@Qualifier("interno-bonabox")
	@Autowired
	private WebClient webClient;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> gravarLog(@RequestBody final LoggerInterno logger) {
		try {

			webClient.post().uri("/logger").body(Mono.just(logger), LoggerInterno.class).retrieve()
					.bodyToMono(LoggerInterno.class).block();

			return new ResponseEntity<Object>(HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

class LoggerInterno {

	private Integer boxId;
	private LocalDateTime dataHora;
	private String content;
	private String correlationId;
	private long timeTaken;

	public LoggerInterno() {
	}

	public LoggerInterno(Integer boxId, LocalDateTime dataHora, String content, String correlationId, long timeTaken) {
		super();
		this.boxId = boxId;
		this.dataHora = dataHora;
		this.content = content;
		this.correlationId = correlationId;
		this.timeTaken = timeTaken;
	}

	public Integer getBoxId() {
		return boxId;
	}

	public void setBoxId(Integer boxId) {
		this.boxId = boxId;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public long getTimeTaken() {
		return timeTaken;
	}

	public void setTimeTaken(long timeTaken) {
		this.timeTaken = timeTaken;
	}

}
