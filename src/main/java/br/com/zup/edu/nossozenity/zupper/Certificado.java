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
public class Certificado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String instituicaoEmissora;

    @Column(nullable = false)
    private String link;

    @ManyToOne(optional = false)
    private Zupper dono;

    @Enumerated(EnumType.STRING)
    private TipoCertificado tipo;

    public Certificado(String nome, String instituicaoEmissora, String link, Zupper zupper,
                       TipoCertificado tipo) {
        this.nome = nome;
        this.instituicaoEmissora = instituicaoEmissora;
        this.link = link;
        this.dono = zupper;
        this.tipo = tipo;
    }

    /**
     * @deprecated construtor para uso exclusivo do hibernate
     */
    @Deprecated
    public Certificado() {}

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getInstituicaoEmissora() {
        return instituicaoEmissora;
    }

    public String getLink() {
        return link;
    }

    public TipoCertificado getTipo() {
        return tipo;
    }

}
