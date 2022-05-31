package br.com.zup.edu.nossozenity.zupper;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import org.assertj.core.groups.Tuple;
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
public class ListarKudosRecebidosDoZupperControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ZupperRepository zupperRepository;

    @Autowired
    private KudoRepository kudoRepository;

    private Zupper zupperRemetente;
    private Zupper zupperDestinatario;

    @BeforeEach
    void setUp() {
        kudoRepository.deleteAll();
        zupperRepository.deleteAll();

        zupperRemetente = new Zupper(
            "José", "Developer", LocalDate.now().minusMonths(3), "jose@zup.com.br"
        );

        zupperDestinatario = new Zupper(
            "Thiago", "Developer", LocalDate.now().minusMonths(3), "thiago@zup.com.br"
        );

        zupperRepository.save(zupperRemetente);
        zupperRepository.save(zupperDestinatario);
    }

    @Test
    void deveListarOsKudosRecebidosDeUmZupper() throws Exception {
        // cenário (given)
        //
        Kudo kudo1 = new Kudo(TipoKudo.TRABALHO_EM_EQUIPE, zupperRemetente, zupperDestinatario);
        Kudo kudo2 = new Kudo(TipoKudo.IDEIAS_ORIGINAIS, zupperRemetente, zupperDestinatario);
        kudoRepository.saveAll(List.of(kudo1, kudo2));

        MockHttpServletRequestBuilder request = get(
            "/zupper/{id}/kudos/recebidos", zupperDestinatario.getId()
        ).contentType(APPLICATION_JSON);

        // ação (when) e corretude (then)
        //
        String response = mockMvc.perform(request)
                                 .andExpect(status().isOk())
                                 .andReturn()
                                 .getResponse()
                                 .getContentAsString(UTF_8);

        TypeFactory typeFactory = objectMapper.getTypeFactory();
        List<KudoResponse> kudos = objectMapper.readValue(
            response, typeFactory.constructCollectionType(List.class, KudoResponse.class)
        );

        assertThat(kudos).hasSize(2)
                         .extracting("nome", "enviadoPor")
                         .contains(
                             new Tuple(
                                 kudo1.getNome().name().toLowerCase(Locale.ROOT),
                                 kudo1.getEnviado().getNome()
                             ),
                             new Tuple(
                                 kudo2.getNome().name().toLowerCase(Locale.ROOT),
                                 kudo2.getEnviado().getNome()
                             )
                         );
    }

    @Test
    void deveRetornarUmaColecaoVaziaParaUmZupperSemKudosRecebidos() throws Exception {
        // cenário (given)
        //
        MockHttpServletRequestBuilder request = get(
            "/zupper/{id}/kudos/recebidos", zupperDestinatario.getId()
        ).contentType(APPLICATION_JSON);

        // ação (when) e corretude (then)
        //
        String response = mockMvc.perform(request)
                                 .andExpect(status().isOk())
                                 .andReturn()
                                 .getResponse()
                                 .getContentAsString(UTF_8);

        TypeFactory typeFactory = objectMapper.getTypeFactory();
        List<KudoResponse> kudos = objectMapper.readValue(
            response, typeFactory.constructCollectionType(List.class, KudoResponse.class)
        );

        assertThat(kudos).isEmpty();
    }

    @Test
    void naoDeveListarOsKudosRecebidosDeUmZupperQueNaoEstaCadastrado() throws Exception {
        // cenário (given)
        //
        MockHttpServletRequestBuilder request = get(
            "/zupper/{id}/kudos/recebidos", Integer.MAX_VALUE
        ).contentType(APPLICATION_JSON);

        // ação (when) e corretude (then)
        //
        Exception resolvedException = mockMvc.perform(request)
                                             .andExpect(status().isNotFound())
                                             .andReturn()
                                             .getResolvedException();

        assertNotNull(resolvedException);
        assertEquals(ResponseStatusException.class, resolvedException.getClass());
        assertEquals(
            "Zupper não cadastrado.", ((ResponseStatusException) resolvedException).getReason()
        );
    }

}
