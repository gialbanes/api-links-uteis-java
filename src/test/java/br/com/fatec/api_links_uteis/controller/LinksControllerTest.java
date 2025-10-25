package br.com.fatec.api_links_uteis.controller;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Testes unitários simples para o LinksController.
 *
 * Esta classe testa os métodos do controller diretamente,
 * sem usar o contexto do Spring ou simulação HTTP.
 *
 * Ideal para testar a lógica de negócio isoladamente.
 */
class LinksControllerTest {

    private final LinksController controller = new LinksController(); // Instancia o controller diretamente

    // 1. Teste para listar todos os links
    @Test
    void deveRetornarTodosOsLinks() {
        // uma lista de mapas representando os links
        List<Map<String, String>> links = controller.getLinks();
        // Verifica se a lista contém os links esperados
        assertEquals(2, links.size());
        // Verifica o conteúdo do primeiro link
        assertEquals("GitHub", links.get(0).get("titulo"));
    }

    // 2. Teste para buscar link por ID existente
    @Test
    void deveRetornarLinkQuandoIdExiste() {
        // um mapa representando o link
        Map<String, String> link = controller.getLinkById(1);
        // Verifica os atributos do link retornado
        assertEquals("1", link.get("id"));
        assertEquals("GitHub", link.get("titulo"));
    }

    // 3. Teste para buscar link por ID inexistente
    @Test
    void deveRetornarNullQuandoIdNaoExiste() {
        // um mapa representando o link
        Map<String, String> link = controller.getLinkById(999);
        // Verifica que o link retornado é null
        assertEquals(null, link);
    }

    // 4. Teste para criar novo link
    @Test
    void deveCriarNovoLinkComIdGerado() {
        // um mapa representando os dados do novo link
        Map<String, String> novoLink = Map.of("titulo", "Google", "url", "https://google.com");
        // um mapa representando o link criado
        Map<String, String> resultado = controller.createLink(novoLink);

        // verifica os atributos do link criado
        assertEquals("Google", resultado.get("titulo"));
        assertEquals("https://google.com", resultado.get("url"));
        assertEquals("3", resultado.get("id")); // Próximo ID
    }

    // 5. Teste para atualizar link existente
    @Test
    void deveAtualizarLinkExistenteCompletamente() {
        // um mapa representando os dados para atualização do link
        Map<String, String> dadosAtualizacao = Map.of("titulo", "Novo Título", "url", "https://novo.com");
        // um mapa representando o link atualizado
        Map<String, String> resultado = controller.updateLink(1, dadosAtualizacao);

        // verifica os atributos do link atualizado
        assertEquals("Novo Título", resultado.get("titulo"));
        assertEquals("https://novo.com", resultado.get("url"));
    }

    // 6. Teste para atualizar parcialmente (PATCH)
    @Test
    void deveAtualizarLinkParcialmente() {
        // um mapa representando os dados parciais para atualização do link
        Map<String, String> dadosParciais = Map.of("titulo", "Título Atualizado");
        // um mapa representando o link atualizado
        Map<String, String> resultado = controller.patchLink(2, dadosParciais);

        // verifica os atributos do link atualizado
        assertEquals("Título Atualizado", resultado.get("titulo"));
        assertEquals("https://stackoverflow.com", resultado.get("url")); // URL original mantida
    }

    // 7. Teste para PATCH com link inexistente
    @Test
    void deveRetornarNullQuandoPatchLinkInexistente() {
        // um mapa representando os dados parciais para atualização do link
        Map<String, String> dadosParciais = Map.of("titulo", "Teste");
        // um mapa representando o link atualizado
        Map<String, String> resultado = controller.patchLink(999, dadosParciais);

        // verifica que o resultado é null
        assertEquals(null, resultado);
    }

    // 8. Teste para deletar link
    @Test
    void deveDeletarLinkERetornarMensagemConfirmacao() {
        // chama o método de deleteLink 
        String resultado = controller.deleteLink(1);
        // verifica a mensagem de confirmação
        assertEquals("Link 1 removido", resultado);
    }

}