package br.com.bonabox.condominio.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "BlocoEntity")
@Table(name = "bloco")
public class Bloco {

    @Id
    @Column(name = "bloco_id", unique = true)
    private Integer blocoId;

    @Column(name = "label")
    private String label;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    public Bloco() {

    }

    public Bloco(Integer blocoId, String label, String nome, String descricao) {
        super();
        this.blocoId = blocoId;
        this.label = label;
        this.nome = nome;
        this.descricao = descricao;
    }

    public Integer getBlocoId() {
        return blocoId;
    }

    public void setBlocoId(Integer blocoId) {
        this.blocoId = blocoId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}

