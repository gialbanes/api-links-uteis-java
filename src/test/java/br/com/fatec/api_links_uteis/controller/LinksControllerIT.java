package br.com.fatec.api_links_uteis.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// 1. Configura o ambiente de teste. @WebMvcTest foca apenas no teste da camada
// MVC (Controllers), inicializando apenas o LinksController e as dependências
// relevantes para a web.
@WebMvcTest(LinksController.class)
// Garante que o contexto da aplicação (incluindo o estado do linksDB no controller)
// seja recarregado (limpo) após a execução de CADA método de teste. Isso evita
// que um teste afete o resultado de outro.
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class LinksControllerIT {

    // Injeta a instância de MockMvc, que é usada para simular requisições HTTP
    // sem a necessidade de iniciar um servidor HTTP real.
    @Autowired
    private MockMvc mockMvc;

    // Marca o método como um método de teste JUnit 5.
    @Test
    void getAllLinks () throws Exception {
        // Executa a requisição GET para o endpoint /api/links usando o MockMvc.
        mockMvc.perform(get("/api/links"))
        // Começa a cadeia de asserções (verificações) na resposta HTTP.
        // Espera que o status da resposta seja 200 OK.
        .andExpect(status().isOk())
        // Espera que o corpo da resposta JSON seja um array (o '$' representa o corpo inteiro).
        .andExpect(jsonPath("$").isArray())
        // Espera que o tamanho do array JSON (a lista de links) seja exatamente 2.
        .andExpect(jsonPath("$.length()").value(2))
        // Espera que o campo 'titulo' do primeiro objeto (índice 0) seja "GitHub".
        .andExpect(jsonPath("$[0].titulo").value("GitHub"))
        // Espera que o campo 'titulo' do segundo objeto (índice 1) seja "Stack Overflow".
        .andExpect(jsonPath("$[1].titulo").value("Stack Overflow"));
    }
}