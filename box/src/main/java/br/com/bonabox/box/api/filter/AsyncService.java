package br.com.bonabox.box.api.filter;

import br.com.bonabox.box.api.dataproviders.LoggerGateway;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AsyncService implements AsyncServiceInterface {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final LoggerGateway loggerGateway;

	public AsyncService(LoggerGateway loggerGateway) {
		super();
		this.loggerGateway = loggerGateway;
	}

	@Async("interno-box-paralelo-TaskExecutor")
	public void sendLogger(String inputLogger, String correlationId, long timeTaken, String url, String metodo) {
		try {

			ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JSR310Module());
			mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

			String operationName = "";
			
			if(url != null) {
				if(url.endsWith("box/owner") && "GET".equals(metodo)) {
					operationName = "INT-box CONS box owner";
				} else if(url.contains("box-install") && "GET".equals(metodo)) {
					operationName = "INT-box CONS box-install";
				} else if(url.contains("/box") && url.split("/").length == 9 && "GET".equals(metodo)) {
					operationName = "BFF CONS consultarByNumeroSerial";
				}else if(url.contains("/box") && url.split("/").length == 8 && "GET".equals(metodo)) {
						operationName = "BFF CONS consultarCompartimentos";
				}
			}
			
			String input = mapper.writeValueAsString(
					new LoggerInterno(0, LocalDateTime.now(), inputLogger, correlationId, timeTaken, operationName));

			loggerGateway.sendLogger(input);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}

interface AsyncServiceInterface {
	public void sendLogger(String inputLogger, String correlationId, long timeTaken, String url, String metodo);
}

class LoggerInterno {

	private Integer boxId;
	private LocalDateTime dataHora;
	private String content;
	private String correlationId;
	private long timeTaken;
	private String operationName;

	public LoggerInterno() {
	}



	public LoggerInterno(Integer boxId, LocalDateTime dataHora, String content, String correlationId, long timeTaken,
			String operationName) {
		super();
		this.boxId = boxId;
		this.dataHora = dataHora;
		this.content = content;
		this.correlationId = correlationId;
		this.timeTaken = timeTaken;
		this.operationName = operationName;
	}

	


	public String getOperationName() {
		return operationName;
	}



	public void setOperationName(String operationName) {
		this.operationName = operationName;
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
