package br.com.zup.edu.nossozenity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.zup.edu.nossozenity.repositories.CertificadoRepository;
import br.com.zup.edu.nossozenity.repositories.HabilidadeRepository;
import br.com.zup.edu.nossozenity.repositories.KudoRepository;
import br.com.zup.edu.nossozenity.repositories.ZupperRepository;
import br.com.zup.edu.nossozenity.zupper.Certificado;
import br.com.zup.edu.nossozenity.zupper.Habilidade;
import br.com.zup.edu.nossozenity.zupper.Kudo;
import br.com.zup.edu.nossozenity.zupper.NivelHabilidade;
import br.com.zup.edu.nossozenity.zupper.TipoCertificado;
import br.com.zup.edu.nossozenity.zupper.TipoKudo;
import br.com.zup.edu.nossozenity.zupper.Zupper;

@Component
public class DataLoader implements CommandLineRunner {

    private final ZupperRepository zupperRepository;
    private final KudoRepository kudoRepository;
    private final CertificadoRepository certificadoRepository;
    private final HabilidadeRepository habilidadeRepository;

    public DataLoader(ZupperRepository zupperRepository, KudoRepository kudoRepository,
                      CertificadoRepository certificadoRepository,
                      HabilidadeRepository habilidadeRepository) {
        this.zupperRepository = zupperRepository;
        this.kudoRepository = kudoRepository;
        this.certificadoRepository = certificadoRepository;
        this.habilidadeRepository = habilidadeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Zupper zupper1 = new Zupper(
            "Thiago", "Developer", LocalDate.now().minusYears(1).minusMonths(2).minusDays(3),
            "thiago@zup.com.br"
        );
        Zupper zupper2 = new Zupper("Eloy", "Manager", LocalDate.now(), "eloy@zup.com.br");

        zupperRepository.save(zupper1);
        zupperRepository.save(zupper2);

        Kudo kudo1 = new Kudo(TipoKudo.TRABALHO_EM_EQUIPE, LocalDateTime.now().minusDays(7));
        kudo1.setEnviado(zupper2);
        kudo1.setRecebido(zupper1);
        Kudo kudo2 = new Kudo(TipoKudo.LIDERANCA_INSPIRADORA, LocalDateTime.now().minusDays(14));
        kudo2.setEnviado(zupper1);
        kudo2.setRecebido(zupper2);

        kudoRepository.save(kudo1);
        kudoRepository.save(kudo2);

        zupper1.getKudosRecebidos().add(kudo1);
        zupper2.getKudosRecebidos().add(kudo2);

        zupperRepository.save(zupper1);
        zupperRepository.save(zupper2);

        Certificado certificado1 = new Certificado(
            "Java Moderno", "Alura", "https://www.example.com", TipoCertificado.CURSO
        );
        certificado1.setDono(zupper1);
        Certificado certificado2 = new Certificado(
            "Liderança Moderna", "Alura", "https://www.example.com", TipoCertificado.OUTROS
        );
        certificado2.setDono(zupper2);

        certificadoRepository.save(certificado1);
        certificadoRepository.save(certificado2);

        zupper1.getCertificados().add(certificado1);
        zupper2.getCertificados().add(certificado2);

        zupperRepository.save(zupper1);
        zupperRepository.save(zupper2);

        Habilidade habilidade1 = new Habilidade("Java", NivelHabilidade.B1);
        habilidade1.setDonoHabilidade(zupper1);
        Habilidade habilidade2 = new Habilidade("Liderança", NivelHabilidade.B2);
        habilidade2.setDonoHabilidade(zupper2);

        habilidadeRepository.save(habilidade1);
        habilidadeRepository.save(habilidade2);

        zupper1.getHabilidades().add(habilidade1);
        zupper2.getHabilidades().add(habilidade2);

        zupperRepository.save(zupper1);
        zupperRepository.save(zupper2);
    }

}
