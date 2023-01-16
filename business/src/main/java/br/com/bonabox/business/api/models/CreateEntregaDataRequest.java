package br.com.bonabox.business.api.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateEntregaDataRequest {

	@Size(min = 1, max = 32)
	@NotNull
	private String numeroSerial;
	private Integer empresaEntregadora;

	public CreateEntregaDataRequest() {
	}

	public CreateEntregaDataRequest(String numeroSerial, Integer empresaEntregadora) {
		super();
		this.numeroSerial = numeroSerial;
		this.empresaEntregadora = empresaEntregadora;
	}

	public String getNumeroSerial() {
		return numeroSerial;
	}

	public void setNumeroSerial(String numeroSerial) {
		this.numeroSerial = numeroSerial;
	}
	
	public Integer getEmpresaEntregadora() {
		return empresaEntregadora;
	}

	public void setEmpresaEntregadora(Integer empresaEntregadora) {
		this.empresaEntregadora = empresaEntregadora;
	}

}
