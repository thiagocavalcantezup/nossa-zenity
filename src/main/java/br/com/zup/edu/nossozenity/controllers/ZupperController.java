package br.com.zup.edu.nossozenity.controllers;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.zup.edu.nossozenity.repositories.ZupperRepository;
import br.com.zup.edu.nossozenity.zupper.Zupper;
import br.com.zup.edu.nossozenity.zupper.ZupperPatchDTO;

@RestController
@RequestMapping(ZupperController.BASE_URI)
public class ZupperController {

    public final static String BASE_URI = "/zuppers";

    private final ZupperRepository zupperRepository;

    public ZupperController(ZupperRepository zupperRepository) {
        this.zupperRepository = zupperRepository;
    }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<Void> patch(@PathVariable Long id,
                                      @RequestBody @Valid ZupperPatchDTO zupperPatchDTO) {
        Zupper zupper = zupperRepository.findById(id)
                                        .orElseThrow(
                                            () -> new ResponseStatusException(
                                                HttpStatus.NOT_FOUND,
                                                "Não existe um zupper com o id fornecido."
                                            )
                                        );

        zupper.setNome(zupperPatchDTO.getNome());
        zupper.setCargo(zupperPatchDTO.getCargo());
        zupperRepository.save(zupper);

        return ResponseEntity.noContent().build();
    }

}
