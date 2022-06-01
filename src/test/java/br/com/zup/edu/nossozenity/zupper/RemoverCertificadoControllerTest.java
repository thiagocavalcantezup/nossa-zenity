package br.com.zup.edu.nossozenity.zupper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.server.ResponseStatusException;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ActiveProfiles("test")
public class RemoverCertificadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CertificadoRepository certRepository;

    @Autowired
    private ZupperRepository zupperRepository;

    @BeforeEach
    void setUp() {
        certRepository.deleteAll();
    }

    @Test
    void deveRemoverUmCertificado() throws Exception {
        // cenário (given)
        //
        Zupper zupper = new Zupper("Thiago", "Developer", LocalDate.now(), "thiago@zup.edu");
        zupper = zupperRepository.save(zupper);

        Certificado cert = new Certificado(
            "Testes de Integração com Java", "Alura", "https://example.com/", zupper,
            TipoCertificado.CURSO
        );
        cert = certRepository.save(cert);

        MockHttpServletRequestBuilder request = delete(
            "/certificados/{id}", cert.getId()
        ).contentType(APPLICATION_JSON);

        // ação (when) e corretude (then)
        //
        mockMvc.perform(request).andExpect(status().isNoContent());

        assertFalse(
            certRepository.existsById(cert.getId()),
            "Não deve existir registro de certificado pra esse id."
        );
    }

    @Test
    void naoDeveRemoverUmCertificadoQueNaoEstaCadastrado() throws Exception {
        // cenário (given)
        //
        MockHttpServletRequestBuilder request = delete(
            "/certificados/{id}", Integer.MAX_VALUE
        ).contentType(APPLICATION_JSON);

        // ação (when) e corretude (then)
        //
        Exception resolvedException = mockMvc.perform(request)
                                             .andExpect(status().isNotFound())
                                             .andReturn()
                                             .getResolvedException();

        assertNotNull(resolvedException);
        assertEquals(ResponseStatusException.class, resolvedException.getClass());
        ResponseStatusException responseStatusException = (ResponseStatusException) resolvedException;
        assertEquals("Certificado não cadastrado.", responseStatusException.getReason());
    }

}
