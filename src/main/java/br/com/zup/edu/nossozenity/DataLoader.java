package br.com.zup.edu.nossozenity;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.zup.edu.nossozenity.repositories.ZupperRepository;
import br.com.zup.edu.nossozenity.zupper.Zupper;

@Component
public class DataLoader implements CommandLineRunner {

    private final ZupperRepository zupperRepository;

    public DataLoader(ZupperRepository zupperRepository) {
        this.zupperRepository = zupperRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Zupper zupper = new Zupper("Thiago", "Developer", LocalDate.now(), "thiago@zup.com.br");

        zupperRepository.save(zupper);
    }

}
