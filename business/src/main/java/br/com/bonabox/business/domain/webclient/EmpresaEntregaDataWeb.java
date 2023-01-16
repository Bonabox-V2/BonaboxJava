package br.com.bonabox.business.domain.webclient;

public class EmpresaEntregaDataWeb {

	private Integer empresaId;
	private String nome;
	private String razaoSocial;
	private String responsavel;
	private String telefoneResponsavel;
	private String statusEmpresa;

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

	public String getStatusEmpresa() {
		return statusEmpresa;
	}

	public void setStatusEmpresa(String statusEmpresa) {
		this.statusEmpresa = statusEmpresa;
	}

}

