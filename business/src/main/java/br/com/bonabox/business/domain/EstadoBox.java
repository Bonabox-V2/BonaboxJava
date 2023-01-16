package br.com.bonabox.business.domain;

public final class EstadoBox {

	private int boxId;
	private Integer condominioId;
	private String tipoBox;
	private int boxOwner;
	private int compartimentoId;
	private String labelPorta;
	private int compartimentoCom1Id;
	private String compartimentoTamanho;
	private EstadoBoxAtividade estadoAtividade;

	public EstadoBox() {

	}

	public EstadoBox(int boxId, Integer condominioId, String tipoBox, int boxOwner, int compartimentoId,
			String labelPorta, int compartimentoCom1Id, String compartimentoTamanho,
			EstadoBoxAtividade estadoAtividade) {
		super();
		this.boxId = boxId;
		this.condominioId = condominioId;
		this.tipoBox = tipoBox;
		this.boxOwner = boxOwner;
		this.compartimentoId = compartimentoId;
		this.labelPorta = labelPorta;
		this.compartimentoCom1Id = compartimentoCom1Id;
		this.compartimentoTamanho = compartimentoTamanho;
		this.estadoAtividade = estadoAtividade;
	}

	public int getCompartimentoCom1Id() {
		return compartimentoCom1Id;
	}

	public void setCompartimentoCom1Id(int compartimentoCom1Id) {
		this.compartimentoCom1Id = compartimentoCom1Id;
	}

	public int getBoxId() {
		return boxId;
	}

	public void setBoxId(int boxId) {
		this.boxId = boxId;
	}

	public Integer getCondominioId() {
		return condominioId;
	}

	public void setCondominioId(Integer condominioId) {
		this.condominioId = condominioId;
	}

	public String getTipoBox() {
		return tipoBox;
	}

	public void setTipoBox(String tipoBox) {
		this.tipoBox = tipoBox;
	}

	public int getBoxOwner() {
		return boxOwner;
	}

	public void setBoxOwner(int boxOwner) {
		this.boxOwner = boxOwner;
	}

	public int getCompartimentoId() {
		return compartimentoId;
	}

	public void setCompartimentoId(int compartimentoId) {
		this.compartimentoId = compartimentoId;
	}

	public String getLabelPorta() {
		return labelPorta;
	}

	public void setLabelPorta(String labelPorta) {
		this.labelPorta = labelPorta;
	}

	public String getCompartimentoTamanho() {
		return compartimentoTamanho;
	}

	public void setCompartimentoTamanho(String compartimentoTamanho) {
		this.compartimentoTamanho = compartimentoTamanho;
	}

	public EstadoBoxAtividade getEstadoAtividade() {
		return estadoAtividade;
	}

	public void setEstadoAtividade(EstadoBoxAtividade estadoAtividade) {
		this.estadoAtividade = estadoAtividade;
	}

}
