package br.com.zup.edu.nossozenity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zup.edu.nossozenity.zupper.Kudo;

public interface KudoRepository extends JpaRepository<Kudo, Long> {

}
