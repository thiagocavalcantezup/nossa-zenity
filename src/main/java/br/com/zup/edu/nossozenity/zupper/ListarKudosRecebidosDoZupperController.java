package br.com.zup.edu.nossozenity.zupper;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class ListarKudosRecebidosDoZupperController {
    private final KudoRepository repository;

    public ListarKudosRecebidosDoZupperController(KudoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/zupper/{id}/kudos/recebidos")
    public ResponseEntity<?> listar(@PathVariable Long id){
        List<KudoResponse> response = repository.findAllRecebidoId(id)
                .stream()
                .map(KudoResponse::new)
                .collect(Collectors.toList());

        return ok(response);
    }
}
