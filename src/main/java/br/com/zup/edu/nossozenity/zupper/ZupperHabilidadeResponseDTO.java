package br.com.zup.edu.nossozenity.zupper;

public class ZupperHabilidadeResponseDTO {

    private String nome;
    private String nivel;

    public ZupperHabilidadeResponseDTO() {}

    public ZupperHabilidadeResponseDTO(Habilidade habilidade) {
        this.nome = habilidade.getNome();
        this.nivel = habilidade.getNivel().name();
    }

    public String getNome() {
        return nome;
    }

    public String getNivel() {
        return nivel;
    }

}
