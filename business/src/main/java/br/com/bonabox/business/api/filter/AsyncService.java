package br.com.bonabox.business.api.filter;

import br.com.bonabox.business.dataproviders.LoggerGateway;
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

	@Async("bonabox-business-paralelo-TaskExecutor")
	public void sendLogger(String inputLogger, String correlationId, long timeTaken, String url, String metodo) {
		try {

			ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JSR310Module());
			mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

			String operationName = "";
			
			if(url != null) {
				if(url.endsWith("box/api/v1/entrega") && "POST".equals(metodo)) {
					operationName = "BFF CREATE Entrega";
				} else if(url.endsWith("box/api/v1/entrega") && "PUT".equals(metodo)) {
					operationName = "BFF UPDATE Entrega";
				} else if(url.endsWith("box/api/v1/entrega") && url.split("/").length == 8 && "PUT".equals(metodo)) {
					operationName = "BFF FINALIZAR Entrega";
				} else if(url.endsWith("box/api/v1/entregador") && "POST".equals(metodo)) {
					operationName = "BFF CREATE entregador";
				} else if(url.contains("box/api/v1/status-entrega") && url.split("/").length == 8 && "GET".equals(metodo)) {
					operationName = "BFF CONS Status Entrega";
				} else if(url.endsWith("box/api/v1/status-entrega") && "POST".equals(metodo)) {
					operationName = "BFF CREATE Status Entrega";
				} else if(url.contains("/obter-tipo-compartimentos-disponiveis") && "GET".equals(metodo)) {
					operationName = "BFF CONS tamanho de compartimentos disponíveis";
				} else if(url.contains("locker/obter-compartimento") && "GET".equals(metodo)) {
					operationName = "BFF CONS porta a ser aberta";
				}} else if(url.endsWith("box/api/v1/view/notification") && "GET".equals(metodo)) {
					operationName = "BFF VIEW QRCode";
				} else if(url.endsWith("box/api/v1/retirada/porta/") && "GET".equals(metodo)) {
					operationName = "BFF CONS ";
				} else if(url.endsWith("box/api/v1/retirada/porta/token") && "GET".equals(metodo)) {
					operationName = "BFF CONS porta/token";
				} else if(url.endsWith("box/api/v1/retirada/liberar-porta") && "PUT".equals(metodo)) {
					operationName = "BFF liberar-porta";
				} else if(url.endsWith("box/api/v1/retirada/processar-abertura-compartimento") && "PUT".equals(metodo)) {
					operationName = "BFF processar-abertura-compartimento";
				} else if(url.endsWith("box/api/v1/retirada/porta-fechada") && "POST".equals(metodo)) {
					operationName = "BFF retirada/porta-fechada";
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
	void sendLogger(String inputLogger, String correlationId, long timeTaken, String url, String metodo);
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
