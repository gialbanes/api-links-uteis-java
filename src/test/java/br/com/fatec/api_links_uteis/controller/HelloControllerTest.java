package br.com.fatec.api_links_uteis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Testes unitários simples para o HelloController.
 *
 * Esta classe testa os métodos do controller diretamente,
 * sem usar o contexto do Spring ou simulação HTTP.
 *
 * Ideal para testar a lógica de negócio isoladamente.
 */
class HelloControllerTest {

    private final HelloController controller = new HelloController(); // Instancia o controller diretamente

    @Test // anotação que indica que este método é um caso de teste
    void deveRetornarMensagemHello() { 
        // Testamos diretamente o retorno do método `hello()`
        String resultado = controller.hello(); // Chama o método do controller
        assertEquals("Olá Fatec", resultado); // Verifica se o retorno é o esperado
    }

}