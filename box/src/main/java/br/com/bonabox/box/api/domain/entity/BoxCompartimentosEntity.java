package br.com.bonabox.box.api.domain.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "BoxCompartimentosEntity")
@Table(name = "box_compartimentos")
public class BoxCompartimentosEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4963288655382203146L;

	@Id
	@Column(name = "codigo", unique = true)
	private int codigo;

	@Column(name = "codigo_compartimento")
	private int codigoCompartimento;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "codigo_compartimento", insertable = false, updatable = false)
	private CompartimentoEntity compartimento;

	@Column(name = "codigo_box")
	private int codigoBox;

	@Column(name = "label")
	private String label;

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public int getCodigoCompartimento() {
		return codigoCompartimento;
	}

	public void setCodigoCompartimento(int codigoCompartimento) {
		this.codigoCompartimento = codigoCompartimento;
	}

	public CompartimentoEntity getCompartimento() {
		return compartimento;
	}

	public void setCompartimento(CompartimentoEntity compartimento) {
		this.compartimento = compartimento;
	}

	public int getCodigoBox() {
		return codigoBox;
	}

	public void setCodigoBox(int codigoBox) {
		this.codigoBox = codigoBox;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
