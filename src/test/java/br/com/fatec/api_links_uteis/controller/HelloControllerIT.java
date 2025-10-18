package br.com.fatec.api_links_uteis.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Testes de integração para o HelloController.
 *
 * Esta classe testa apenas o endpoint de saudação,
 * usando @WebMvcTest para testar apenas a camada web do Spring,
 * simulando requisições HTTP com MockMvc.
 */
@WebMvcTest(HelloController.class) // anotação que configura o teste para o controller específico 
class HelloControllerIT {

    @Autowired // anotação que injeta a dependência do MockMvc
    private MockMvc mockMvc; // instância do MockMvc para simular requisições HTTP

    @Test // anotação que indica que este método é um caso de teste
    void deveRetornarMensagemHelloQuandoGetHelloEndpoint() throws Exception {
        mockMvc.perform(get("/api/hello")) // simula uma requisição GET para o endpoint /api/hello
                .andExpect(status().isOk()) // verifica se o status da resposta é 200 OK
                .andExpect(content().string("Olá Fatec")); // verifica se o conteúdo da resposta é "Olá Fatec"
    }

}