package br.com.bonabox.business.api.filter;

public class DataMDC {

	private String correlationId;
	private String callOrigem;

	public DataMDC() {
	}

	public DataMDC(String correlationId, String callOrigem) {
		super();
		this.correlationId = correlationId;
		this.callOrigem = callOrigem;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public String getCallOrigem() {
		return callOrigem;
	}

	public void setCallOrigem(String callOrigem) {
		this.callOrigem = callOrigem;
	}

}
