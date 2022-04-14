package br.com.zup.edu.nossozenity.zupper;

import javax.validation.constraints.NotBlank;

public class ZupperPatchDTO {

    @NotBlank
    private String nome;

    @NotBlank
    private String cargo;

    public ZupperPatchDTO() {}

    public ZupperPatchDTO(String nome, String cargo) {
        this.nome = nome;
        this.cargo = cargo;
    }

    public String getNome() {
        return nome;
    }

    public String getCargo() {
        return cargo;
    }

}
