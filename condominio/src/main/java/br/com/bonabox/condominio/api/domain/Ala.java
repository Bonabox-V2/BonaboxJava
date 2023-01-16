package br.com.bonabox.condominio.api.domain;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "AlaEntity")
@Table(name = "ala")
public class Ala {

    @Id
    @Column(name = "ala_id", unique = true)
    private Integer alaId;

    @Column(name = "label")
    private String label;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    public Ala() {

    }

    public Ala(Integer alaId, String label, String nome, String descricao) {
        super();
        this.alaId = alaId;
        this.label = label;
        this.nome = nome;
        this.descricao = descricao;
    }

    public Integer getAlaId() {
        return alaId;
    }

    public void setAlaId(Integer alaId) {
        this.alaId = alaId;
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
