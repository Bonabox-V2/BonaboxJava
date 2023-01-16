package br.com.bonabox.business.domain.webclient;

public class EmpresaEntregaDataWebClient {

	private Integer empresaId;

	private String nome;

	private String razaoSocial;

	private String responsavel;

	private String telefoneResponsavel;

	private String statusEntrega;

	public EmpresaEntregaDataWebClient() {

	}

	public EmpresaEntregaDataWebClient(Integer empresaId, String nome, String razaoSocial, String responsavel,
			String telefoneResponsavel, String statusEntrega) {
		super();
		this.empresaId = empresaId;
		this.nome = nome;
		this.razaoSocial = razaoSocial;
		this.responsavel = responsavel;
		this.telefoneResponsavel = telefoneResponsavel;
		this.statusEntrega = statusEntrega;
	}

	public Integer getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Integer empresaId) {
		this.empresaId = empresaId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public String getTelefoneResponsavel() {
		return telefoneResponsavel;
	}

	public void setTelefoneResponsavel(String telefoneResponsavel) {
		this.telefoneResponsavel = telefoneResponsavel;
	}

	public String getStatusEntrega() {
		return statusEntrega;
	}

	public void setStatusEntrega(String statusEntrega) {
		this.statusEntrega = statusEntrega;
	}

}
