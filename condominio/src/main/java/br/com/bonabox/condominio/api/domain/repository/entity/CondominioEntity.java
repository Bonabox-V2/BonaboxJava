package br.com.bonabox.condominio.api.domain.repository.entity;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "CondominioEntity")
@Table(name = "condominio")
public class CondominioEntity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9208732799045515211L;
	
	@Id
	@Column(name = "condominio_id", unique = true)
	private int condominioId;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "area_total_metro2")
	private double areaTotalMetro2;
	
	@Column(name = "portaria_virtual")
	private boolean portariaVirtual;
	
	@Column(name = "habilitado_correios")
	private boolean habilitadoCorreios;
	
	@OneToMany(mappedBy = "condominio", cascade = CascadeType.ALL)
	private Set<CondominioEnderecosEntity> enderecos;

	public int getCondominioId() {
		return condominioId;
	}

	public void setCondominioId(int condominioId) {
		this.condominioId = condominioId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getAreaTotalMetro2() {
		return areaTotalMetro2;
	}

	public void setAreaTotalMetro2(double areaTotalMetro2) {
		this.areaTotalMetro2 = areaTotalMetro2;
	}

	public boolean isPortariaVirtual() {
		return portariaVirtual;
	}

	public void setPortariaVirtual(boolean portariaVirtual) {
		this.portariaVirtual = portariaVirtual;
	}

	public boolean isHabilitadoCorreios() {
		return habilitadoCorreios;
	}

	public void setHabilitadoCorreios(boolean habilitadoCorreios) {
		this.habilitadoCorreios = habilitadoCorreios;
	}

	public Set<CondominioEnderecosEntity> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(Set<CondominioEnderecosEntity> enderecos) {
		this.enderecos = enderecos;
	}
	
	

}
