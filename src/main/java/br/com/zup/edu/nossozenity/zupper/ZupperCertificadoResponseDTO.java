package br.com.zup.edu.nossozenity.zupper;

public class ZupperCertificadoResponseDTO {

    private String nome;
    private String instituicaoEmissora;
    private String link;
    private String tipo;

    public ZupperCertificadoResponseDTO() {}

    public ZupperCertificadoResponseDTO(Certificado certificado) {
        this.nome = certificado.getNome();
        this.instituicaoEmissora = certificado.getInstituicaoEmissora();
        this.link = certificado.getLink();
        this.tipo = certificado.getTipo().name();
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

    public String getTipo() {
        return tipo;
    }

}
