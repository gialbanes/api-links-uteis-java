package br.com.fatec.api_links_uteis.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping("/api/links") 
public class LinksController {

    // Banco de dados simulado
    // Estrutura de dados (Map) que simula um banco de dados em memória.
    // A chave é um Integer (o ID do link), e o valor é um Map<String, String>
    // que representa o próprio link (contendo "id", "titulo", "url").
    // HashMap é usado para permitir acesso rápido aos links por ID. Funciona como um dicionário.
    private final Map<Integer, Map<String, String>> linksDB = new HashMap<>();
    private int nextId = 1; // Variável para controle do próximo ID (não usada após a inicialização).

    // Construtor da classe. É executado quando o Spring inicializa o Controller.
    public LinksController() {
        // Inicializa o banco de dados simulado com dois links fixos.
        linksDB.put(1, Map.of("id", "1", "titulo", "GitHub", "url", "https://github.com"));
        linksDB.put(2, Map.of("id", "2", "titulo", "Stack Overflow", "url", "https://stackoverflow.com"));
        nextId = 3; // Atualiza o próximo ID (embora não haja métodos de adição implementados).
    }

    @GetMapping 
    public List<Map<String, String>> getLinks() {
        // Retorna todos os valores (os objetos link) presentes no mapa linksDB
        // como uma List. O Spring se encarrega de converter esta lista em JSON
        // e enviá-la como resposta HTTP.
        return new ArrayList<>(linksDB.values());
    }
}