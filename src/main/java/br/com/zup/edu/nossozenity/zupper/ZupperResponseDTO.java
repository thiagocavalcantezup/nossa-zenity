package br.com.zup.edu.nossozenity.zupper;

import java.util.Set;
import java.util.stream.Collectors;

public class ZupperResponseDTO {

    private String nome;
    private String cargo;
    private String tempoDeCasa;
    private Set<ZupperCertificadoResponseDTO> certificados;
    private Set<ZupperKudoResponseDTO> kudosRecebidos;
    private Set<ZupperHabilidadeResponseDTO> habilidades;

    public ZupperResponseDTO() {}

    public ZupperResponseDTO(Zupper zupper) {
        this.nome = zupper.getNome();
        this.cargo = zupper.getCargo();
        this.tempoDeCasa = zupper.getTempoDeCasa();
        this.certificados = zupper.getCertificados()
                                  .stream()
                                  .map(ZupperCertificadoResponseDTO::new)
                                  .collect(Collectors.toSet());
        this.kudosRecebidos = zupper.getKudosRecebidos()
                                    .stream()
                                    .map(ZupperKudoResponseDTO::new)
                                    .collect(Collectors.toSet());
        this.habilidades = zupper.getHabilidades()
                                 .stream()
                                 .map(ZupperHabilidadeResponseDTO::new)
                                 .collect(Collectors.toSet());
    }

    public String getNome() {
        return nome;
    }

    public String getCargo() {
        return cargo;
    }

    public String getTempoDeCasa() {
        return tempoDeCasa;
    }

    public Set<ZupperCertificadoResponseDTO> getCertificados() {
        return certificados;
    }

    public Set<ZupperKudoResponseDTO> getKudosRecebidos() {
        return kudosRecebidos;
    }

    public Set<ZupperHabilidadeResponseDTO> getHabilidades() {
        return habilidades;
    }

}
