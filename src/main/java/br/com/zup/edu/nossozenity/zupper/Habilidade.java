package br.com.zup.edu.nossozenity.zupper;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Habilidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    private NivelHabilidade nivel;

    @ManyToOne(optional = false)
    private Zupper donoHabilidade;

    public Habilidade(String nome, NivelHabilidade nivel, Zupper donoHabilidade) {
        this.nome = nome;
        this.nivel = nivel;
        this.donoHabilidade = donoHabilidade;
    }

    /**
     * @deprecated construtor de uso exclusivo para o hibernate
     */
    @Deprecated
    public Habilidade() {}

    public Long getId() {
        return id;
    }

}
