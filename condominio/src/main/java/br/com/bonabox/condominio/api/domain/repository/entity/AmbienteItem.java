package br.com.bonabox.condominio.api.domain.repository.entity;

import java.util.HashSet;
import java.util.Set;

public class AmbienteItem {

	private int codigoAmbienteItem;
	private Ambiente ambiente;
	private Item item;
	private Integer quantidade;
	private Set condominioAmbienteses = new HashSet(0);

	public AmbienteItem() {
	}

	public AmbienteItem(int codigoAmbienteItem, Ambiente ambiente) {
		this.codigoAmbienteItem = codigoAmbienteItem;
		this.ambiente = ambiente;
	}

	public AmbienteItem(int codigoAmbienteItem, Ambiente ambiente, Item item, Integer quantidade,
                        Set condominioAmbienteses) {
		this.codigoAmbienteItem = codigoAmbienteItem;
		this.ambiente = ambiente;
		this.item = item;
		this.quantidade = quantidade;
		this.condominioAmbienteses = condominioAmbienteses;
	}

	public int getCodigoAmbienteItem() {
		return this.codigoAmbienteItem;
	}

	public void setCodigoAmbienteItem(int codigoAmbienteItem) {
		this.codigoAmbienteItem = codigoAmbienteItem;
	}

	public Ambiente getAmbiente() {
		return this.ambiente;
	}

	public void setAmbiente(Ambiente ambiente) {
		this.ambiente = ambiente;
	}

	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Integer getQuantidade() {
		return this.quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Set getCondominioAmbienteses() {
		return this.condominioAmbienteses;
	}

	public void setCondominioAmbienteses(Set condominioAmbienteses) {
		this.condominioAmbienteses = condominioAmbienteses;
	}

}
