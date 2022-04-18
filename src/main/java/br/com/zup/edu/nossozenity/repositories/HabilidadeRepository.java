package br.com.zup.edu.nossozenity.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zup.edu.nossozenity.zupper.Habilidade;

public interface HabilidadeRepository extends JpaRepository<Habilidade, Long> {

    Page<Habilidade> findAllByDonoHabilidadeId(Long id, Pageable pageable);

}
