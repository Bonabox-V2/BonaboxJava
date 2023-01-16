package br.com.bonabox.business.domain.webclient;

public class GenerateDataWebClientRequest {

	private String numeroSerial;
	private String entregaId;
	private String porta;

	public String getNumeroSerial() {
		return numeroSerial;
	}

	public void setNumeroSerial(String numeroSerial) {
		this.numeroSerial = numeroSerial;
	}

	public String getPorta() {
		return porta;
	}

	public void setPorta(String porta) {
		this.porta = porta;
	}

	public String getEntregaId() {
		return entregaId;
	}

	public void setEntregaId(String entregaId) {
		this.entregaId = entregaId;
	}

}
