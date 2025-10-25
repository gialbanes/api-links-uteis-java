package br.com.fatec.api_links_uteis.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Testes de integração para o LinksController.
 *
 * Esta classe testa os endpoints REST da API de links úteis,
 * incluindo operações CRUD completas (GET, POST, PUT, PATCH, DELETE).
 *
 * Usa @WebMvcTest para testar apenas a camada web do Spring,
 * simulando requisições HTTP com MockMvc.
 *
 * @DirtiesContext garante que cada teste tenha um contexto Spring limpo,
 * evitando interferência entre testes devido ao estado compartilhado do HashMap.
 */
@WebMvcTest(LinksController.class) // webMvcTest é usado para testar controladores específicos
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // garante um contexto limpo após cada teste
class LinksControllerIT {

    @Autowired // injeta a dependência do MockMvc
    private MockMvc mockMvc; // instância do MockMvc para simular requisições HTTP

    // 1. Teste para listar todos os links via endpoint
    @Test
    void deveRetornarTodosOsLinksQuandoGetLinksEndpoint() throws Exception {
        // simula uma requisição GET para o endpoint /api/links
        mockMvc.perform(get("/api/links"))
        // verifica se o status da resposta é 200 OK
                .andExpect(status().isOk())
                // verifica se o conteúdo da resposta é uma lista com os links esperados
                .andExpect(jsonPath("$").isArray())
                // verifica se a lista tem tamanho 2 e os títulos corretos
                .andExpect(jsonPath("$.length()").value(2))
                // verifica os títulos dos links na resposta
                .andExpect(jsonPath("$[0].titulo").value("GitHub"))
                .andExpect(jsonPath("$[1].titulo").value("Stack Overflow"));
    }

    // 2. Teste para buscar link por ID existente via endpoint
    @Test
    void deveRetornarLinkQuandoGetLinkPorIdEndpointComIdExistente() throws Exception {
        // simula uma requisição GET para o endpoint /api/links/1
        mockMvc.perform(get("/api/links/1"))
                .andExpect(status().isOk())
                // verifica se o conteúdo da resposta tem os atributos corretos
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.titulo").value("GitHub"))
                .andExpect(jsonPath("$.url").value("https://github.com"));
    }

    // 3. Teste para buscar link por ID inexistente via endpoint
    @Test
    void deveRetornarVazioQuandoGetLinkPorIdEndpointComIdInexistente() throws Exception {
        // simula uma requisição GET para o endpoint /api/links/999 (inexistente)
        mockMvc.perform(get("/api/links/999"))
                .andExpect(status().isOk())
                // verifica que o conteúdo da resposta está vazio
                .andExpect(content().string(""));
    }

    // 4. Teste para criar novo link via endpoint
    @Test
    void deveCriarNovoLinkQuandoPostLinksEndpoint() throws Exception {
        String novoLink = """
                {
                    "titulo": "Google",
                    "url": "https://google.com"
                }
                """;
        // simula uma requisição POST para o endpoint /api/links com o corpo JSON do novo link
        mockMvc.perform(post("/api/links")
        // define o tipo de conteúdo como JSON
                        .contentType(MediaType.APPLICATION_JSON)
                        // adiciona o corpo da requisição
                        .content(novoLink))
                .andExpect(status().isOk())
                // verifica se o conteúdo da resposta tem os atributos corretos do link criado
                .andExpect(jsonPath("$.id").value("3"))
                .andExpect(jsonPath("$.titulo").value("Google"))
                .andExpect(jsonPath("$.url").value("https://google.com"));
    }

    // 5. Teste para atualizar link completo (PUT) via endpoint
    @Test
    void deveAtualizarLinkCompletamenteQuandoPutLinksEndpoint() throws Exception {
        String linkAtualizado = """
                {
                    "titulo": "GitHub Atualizado",
                    "url": "https://github.com/novo"
                }
                """;
        // simula uma requisição PUT para o endpoint /api/links/1 com o corpo JSON do link atualizado
        mockMvc.perform(put("/api/links/1")
                        .contentType(MediaType.APPLICATION_JSON) // define o tipo de conteúdo como JSON
                        .content(linkAtualizado)) // adiciona o corpo da requisição
                .andExpect(status().isOk())
                // verifica se o conteúdo da resposta tem os atributos corretos do link atualizado
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.titulo").value("GitHub Atualizado"))
                .andExpect(jsonPath("$.url").value("https://github.com/novo"));
    }

    // 6. Teste para atualizar parcialmente (PATCH) via endpoint
    @Test
    void deveAtualizarLinkParcialmenteQuandoPatchLinksEndpoint() throws Exception {
        String atualizacaoParcial = """
                {
                    "titulo": "Só o Título Novo"
                }
                """;
        // simula uma requisição PATCH para o endpoint /api/links/2 com o corpo JSON da atualização parcial
        mockMvc.perform(patch("/api/links/2")
                        .contentType(MediaType.APPLICATION_JSON) // define o tipo de conteúdo como JSON
                        .content(atualizacaoParcial)) // adiciona o corpo da requisição
                .andExpect(status().isOk())
                // verifica se o conteúdo da resposta tem os atributos corretos do link atualizado
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.titulo").value("Só o Título Novo"))
                .andExpect(jsonPath("$.url").value("https://stackoverflow.com")); // URL original mantida
    }

    // 7. Teste para PATCH com link inexistente via endpoint
    @Test
    void deveRetornarVazioQuandoPatchLinksEndpointComIdInexistente() throws Exception {
        String atualizacaoParcial = """
                {
                    "titulo": "Teste"
                }
                """;
        // simula uma requisição PATCH para o endpoint /api/links/999 (inexistente) com o corpo JSON da atualização parcial
        mockMvc.perform(patch("/api/links/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(atualizacaoParcial))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    // 8. Teste para deletar link via endpoint
    @Test
    void deveDeletarLinkQuandoDeleteLinksEndpoint() throws Exception {
        // simula uma requisição DELETE para o endpoint /api/links/1
        mockMvc.perform(delete("/api/links/1"))
                .andExpect(status().isOk())
                // verifica a mensagem de confirmação na resposta
                .andExpect(content().string("Link 1 removido"));
    }

}