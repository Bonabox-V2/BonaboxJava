package br.com.bonabox.business.api.models;

public class CreateEntregadorDataRequest {

	private String ddi;
	private String ddd;
	private String telefone;
	private String nome;
	private String numeroSerial;

	public CreateEntregadorDataRequest() {

	}

	public CreateEntregadorDataRequest(String ddi, String ddd, String telefone, String nome, String numeroSerial) {
		super();
		this.ddi = ddi;
		this.ddd = ddd;
		this.telefone = telefone;
		this.nome = nome;
		this.numeroSerial = numeroSerial;
	}

	public String getNumeroSerial() {
		return numeroSerial;
	}

	public void setNumeroSerial(String numeroSerial) {
		this.numeroSerial = numeroSerial;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDdi() {
		return ddi;
	}

	public void setDdi(String ddi) {
		this.ddi = ddi;
	}

	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

}
