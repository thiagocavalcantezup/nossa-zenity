package br.com.zup.edu.nossozenity.zupper;

import java.time.format.DateTimeFormatter;

public class ZupperKudoResponseDTO {

    private TipoKudo nome;
    private String criadoEm;
    private String enviadoPor;

    public ZupperKudoResponseDTO() {}

    public ZupperKudoResponseDTO(Kudo kudo) {
        this.nome = kudo.getNome();
        this.criadoEm = kudo.getCriadoEm().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        this.enviadoPor = kudo.getEnviado().getNome();
    }

    public TipoKudo getNome() {
        return nome;
    }

    public String getCriadoEm() {
        return criadoEm;
    }

    public String getEnviadoPor() {
        return enviadoPor;
    }

}
