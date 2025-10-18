package br.com.fatec.api_links_uteis.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller REST para gerenciar links úteis da FATEC.
 *
 * Esta classe demonstra operações CRUD básicas usando uma HashMap
 * como armazenamento em memória para fins educacionais.
 *
 * Endpoints disponíveis:
 * - GET /api/links - Lista todos os links
 * - GET /api/links/{id} - Busca link por ID
 * - POST /api/links - Cria novo link
 * - PUT /api/links/{id} - Atualiza link completo
 * - PATCH /api/links/{id} - Atualiza parcialmente um link
 * - DELETE /api/links/{id} - Remove um link
 */
@RestController // anotação que indica que esta classe é um controlador REST
@RequestMapping("/api/links") // anotação que define o caminho base para os endpoints deste controlador
public class LinksController {
    private static final String TITULO_GLOBAL = "titulo"; // Título usado nos testes

    // "Banco de dados" simulado
    // map é uma estrutura de dados que armazena pares chave-valor
    // aqui usamos Integer como chave (ID do link) e Map<String, String> como valor (dados do link) que seriam título e URL
    // hashmap é uma implementação concreta de map que usa uma tabela hash para armazenar os dados
    private final Map<Integer, Map<String, String>> linksDB = new HashMap<>();
    private int nextId = 1; 

    public LinksController() {
        // Dados iniciais
        linksDB.put(1, Map.of("id", "1", TITULO_GLOBAL, "GitHub", "url", "https://github.com"));
        linksDB.put(2, Map.of("id", "2", TITULO_GLOBAL, "Stack Overflow", "url", "https://stackoverflow.com"));
        nextId = 3;
    }

    // GET - Listar todos os links
    @GetMapping
    // cada elemento da lista é um mapa que representa um link com seus atributos de id, título e url
    public List<Map<String, String>> getLinks() {
        // cria e devolve uma nova lista contendo todos os valores (links) do mapa linksDB
        return new ArrayList<>(linksDB.values());
    }

    // GET - Buscar link por ID
    @GetMapping("/{id}") 
    // o link é representado como um mapa de strings, onde as chaves são os nomes dos atributos (id, título, url)
    // @pathvariable indica que o valor do parâmetro id virá da URL da requisição
    public Map<String, String> getLinkById(@PathVariable int id) {
        // retorna o link correspondente ao ID fornecido, ou null se não existir
        return linksDB.get(id);
    }

    // POST - Criar novo link
    @PostMapping
    // Map<String, String> é o que sera retornado, nesse caso um mapa representando o novo link criado
    // @requestbody indica que os dados do novo link virão do corpo da requisição HTTP
    public Map<String, String> createLink(@RequestBody Map<String, String> data) {
        // cria um novo mapa para o link 
        Map<String, String> novoLink = new HashMap<>();
        // pegando os atributos do link a partir do corpo da requisição
        novoLink.put("id", String.valueOf(nextId));
        novoLink.put(TITULO_GLOBAL, data.get(TITULO_GLOBAL));
        novoLink.put("url", data.get("url"));

        // adiciona o novo link ao "banco de dados"
        linksDB.put(nextId, novoLink);
        nextId++;

        return novoLink;
    }

    // PUT - Atualizar link completo
    @PutMapping("/{id}")
    public Map<String, String> updateLink(@PathVariable int id, @RequestBody Map<String, String> data) {
        // cria um novo mapa para o link atualizado
        Map<String, String> linkAtualizado = new HashMap<>();
        // atualiza todos os atributos do link com os novos valores fornecidos no corpo da requisição
        linkAtualizado.put("id", String.valueOf(id));
        linkAtualizado.put(TITULO_GLOBAL, data.get(TITULO_GLOBAL));
        linkAtualizado.put("url", data.get("url"));

        // substitui o link existente no "banco de dados" pelo link atualizado
        linksDB.put(id, linkAtualizado);
        return linkAtualizado;
    }

    // PATCH - Atualizar parcialmente
    @PatchMapping("/{id}")
    public Map<String, String> patchLink(@PathVariable int id, @RequestBody Map<String, String> data) {
        // obtém o link existente 
        Map<String, String> linkExistente = linksDB.get(id);
        // se o link existir, atualiza apenas os campos fornecidos no corpo da requisição
        if (linkExistente != null) {
            Map<String, String> linkAtualizado = new HashMap<>(linkExistente);
            if (data.get(TITULO_GLOBAL) != null) linkAtualizado.put(TITULO_GLOBAL, data.get(TITULO_GLOBAL));
            if (data.get("url") != null) linkAtualizado.put("url", data.get("url"));

            // salva o link atualizado no "banco de dados"
            linksDB.put(id, linkAtualizado);
            return linkAtualizado;
        }
        return null;
    }

    // DELETE - Remover link
    @DeleteMapping("/{id}")
    public String deleteLink(@PathVariable int id) {
        // remove o link do "banco de dados"
        linksDB.remove(id);
        return "Link " + id + " removido";
    }

}