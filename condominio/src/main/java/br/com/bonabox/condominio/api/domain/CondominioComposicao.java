package br.com.bonabox.condominio.api.domain;

public class CondominioComposicao {

	private Integer condominioComposicaoId;
	private Integer condominioId;
	private Integer alaId;
	private Integer blocoId;
	private Integer unidadeId;
	
	public CondominioComposicao() {
		
	}
	
	public CondominioComposicao(Integer condominioComposicaoId, Integer condominioId, Integer alaId, Integer blocoId,
			Integer unidadeId) {
		super();
		this.condominioComposicaoId = condominioComposicaoId;
		this.condominioId = condominioId;
		this.alaId = alaId;
		this.blocoId = blocoId;
		this.unidadeId = unidadeId;
	}
	public Integer getCondominioComposicaoId() {
		return condominioComposicaoId;
	}
	public void setCondominioComposicaoId(Integer condominioComposicaoId) {
		this.condominioComposicaoId = condominioComposicaoId;
	}
	public Integer getCondominioId() {
		return condominioId;
	}
	public void setCondominioId(Integer condominioId) {
		this.condominioId = condominioId;
	}
	public Integer getAlaId() {
		return alaId;
	}
	public void setAlaId(Integer alaId) {
		this.alaId = alaId;
	}
	public Integer getBlocoId() {
		return blocoId;
	}
	public void setBlocoId(Integer blocoId) {
		this.blocoId = blocoId;
	}
	public Integer getUnidadeId() {
		return unidadeId;
	}
	public void setUnidadeId(Integer unidadeId) {
		this.unidadeId = unidadeId;
	}
	
	
	
}
