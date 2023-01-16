package br.com.bonabox.business.config;

import com.fasterxml.jackson.annotation.JsonRootName;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.Instant;

@JsonRootName("response")
public class LogResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5785704096356377884L;

	private final String correlationId;
	private final String threadName;
	// @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	// private Instant dateTime;
	private final HttpStatus httpStatus;

	public LogResponse(String correlationId, String threadName, HttpStatus httpStatus) {
		super();
		this.correlationId = correlationId;
		this.threadName = threadName;
		this.httpStatus = httpStatus;
	}

	public Instant getDateTime() {
		return Instant.now();
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public String getThreadName() {
		return threadName;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	@Override
	public String toString() {

		return new StringBuilder().append("{\"correlationId\":\"").append(correlationId).append("\", \"threadName\":\"")
				.append(threadName).append("\", \"httpStatus\":\"").append(httpStatus).append("\"}").toString();
	}

}
