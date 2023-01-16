package br.com.bonabox.business.domain.webclient;

public class PessoaDataWebClient {

	private Integer pessoaId;
	private String nome;
	private String apelido;
	private String documento;
	private Integer tipoDocumento;
	private String numeroCelular;
	private String email;

	public PessoaDataWebClient() {
		
	}
	
	public PessoaDataWebClient(Integer pessoaId, String nome, String apelido, String documento, Integer tipoDocumento,
			String numeroCelular, String email) {
		super();
		this.pessoaId = pessoaId;
		this.nome = nome;
		this.apelido = apelido;
		this.documento = documento;
		this.tipoDocumento = tipoDocumento;
		this.numeroCelular = numeroCelular;
		this.email = email;
	}

	public Integer getPessoaId() {
		return pessoaId;
	}

	public void setPessoaId(Integer pessoaId) {
		this.pessoaId = pessoaId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public Integer getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(Integer tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNumeroCelular() {
		return numeroCelular;
	}

	public void setNumeroCelular(String numeroCelular) {
		this.numeroCelular = numeroCelular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
