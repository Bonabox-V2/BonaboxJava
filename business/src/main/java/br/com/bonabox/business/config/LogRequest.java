package br.com.bonabox.business.config;

import org.springframework.http.HttpMethod;

import java.io.Serializable;
import java.time.Instant;

public class LogRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4480238354043874444L;

	private final String correlationId;
	private final String threadName;
	private final String applicationName;
	private final HttpMethod httpMethod;
	private final String url;

	public LogRequest(String correlationId, String threadName, String applicationName, HttpMethod httpMethod,
			String url) {
		super();
		this.correlationId = correlationId;
		this.threadName = threadName;
		this.applicationName = applicationName;
		this.httpMethod = httpMethod;
		this.url = url;
	}

	public Instant getDateTime() {
		return Instant.now();
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public String getThreadName() {
		return threadName;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public String toString() {

		return new StringBuilder().append("{\"correlationId\":\"").append(correlationId).append("\", \"threadName\":\"")
				.append(threadName).append("\", \"applicationName\":\"").append(applicationName)
				.append("\", \"httpMethod\":\"").append(httpMethod).append("\", \"url\":\"").append(url).append("\"}").toString();
	}

}
