package br.com.fatec.api_links_uteis.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller REST para mensagens de boas-vindas.
 *
 * Esta classe demonstra um endpoint simples de saudação
 * para fins educacionais.
 *
 * Endpoint disponível:
 * - GET /api/hello - Mensagem de boas-vindas
 */
@RestController // anotação que indica que esta classe é um controlador REST
@RequestMapping("/api") // anotação que define o caminho base para os endpoints deste controlador
public class HelloController {

    @GetMapping("hello") // anotação que mapeia requisições GET para o método hello()
    public String hello() {
        return "Olá Fatec";
    }

}
