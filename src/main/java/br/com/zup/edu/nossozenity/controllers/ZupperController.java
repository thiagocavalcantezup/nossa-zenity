package br.com.zup.edu.nossozenity.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.zup.edu.nossozenity.repositories.HabilidadeRepository;
import br.com.zup.edu.nossozenity.repositories.ZupperRepository;
import br.com.zup.edu.nossozenity.zupper.Habilidade;
import br.com.zup.edu.nossozenity.zupper.Zupper;
import br.com.zup.edu.nossozenity.zupper.ZupperHabilidadeResponseDTO;
import br.com.zup.edu.nossozenity.zupper.ZupperPatchDTO;
import br.com.zup.edu.nossozenity.zupper.ZupperResponseDTO;

@RestController
@RequestMapping(ZupperController.BASE_URI)
public class ZupperController {

    public final static String BASE_URI = "/zuppers";

    private final ZupperRepository zupperRepository;
    private final HabilidadeRepository habilidadeRepository;

    public ZupperController(ZupperRepository zupperRepository,
                            HabilidadeRepository habilidadeRepository) {
        this.zupperRepository = zupperRepository;
        this.habilidadeRepository = habilidadeRepository;
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

    @GetMapping("/{id}")
    public ResponseEntity<ZupperResponseDTO> show(@PathVariable Long id) {
        Zupper zupper = zupperRepository.findById(id)
                                        .orElseThrow(
                                            () -> new ResponseStatusException(
                                                HttpStatus.NOT_FOUND,
                                                "Não existe um zupper com o id informado."
                                            )
                                        );

        return ResponseEntity.ok(new ZupperResponseDTO(zupper));
    }

    @GetMapping("/{id}" + HabilidadeController.BASE_URI)
    public ResponseEntity<?> habilidadeIndex(@PathVariable Long id,
                                             @PageableDefault(size = 2, page = 0, sort = "id", direction = Direction.ASC) Pageable paginacao) {
        Page<Habilidade> habilidadesPage = habilidadeRepository.findAllByDonoHabilidadeId(
            id, paginacao
        );
        List<ZupperHabilidadeResponseDTO> habilidadeResponses = habilidadesPage.map(
            ZupperHabilidadeResponseDTO::new
        ).toList();
        PageImpl<ZupperHabilidadeResponseDTO> habilidadesPageImpl = new PageImpl<>(
            habilidadeResponses, paginacao, habilidadesPage.getTotalElements()
        );

        return ResponseEntity.ok(habilidadesPageImpl);
    }

}
