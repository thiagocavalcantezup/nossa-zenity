package br.com.zup.edu.nossozenity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zup.edu.nossozenity.zupper.Zupper;

public interface ZupperRepository extends JpaRepository<Zupper, Long> {

}
