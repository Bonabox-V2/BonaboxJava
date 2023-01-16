package br.com.bonabox.business.domain.webclient;

public class MetricaDataWebClientRequest {
	private final String compartimentoId;
	private final String state;
	private final String serialNumber;

	public MetricaDataWebClientRequest(String compartimentoId, String state, String serialNumber) {
		super();
		this.compartimentoId = compartimentoId;
		this.state = state;
		this.serialNumber = serialNumber;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public String getCompartimentoId() {
		return compartimentoId;
	}

	public String getState() {
		return state;
	}
}
