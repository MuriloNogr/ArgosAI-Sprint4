package br.com.argos.argosaisprint3.controller;

import br.com.argos.argosaisprint3.dto.ProdutoDto;
import br.com.argos.argosaisprint3.model.Produto;
import br.com.argos.argosaisprint3.service.ProdutoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/produtos")
@Validated
@Tag(name = "Produtos", description = "APIs para gerenciar produtos")
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ModelMapper modelMapper;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    @Operation(summary = "Obter todos os produtos", description = "Retorna uma lista de todos os produtos.")
    public List<ProdutoDto> getAllProdutos() {
        return produtoService.findAll().stream()
                .map(this::convertToDtoWithLinks)
                .collect(Collectors.toList());
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{id}")
    @Operation(summary = "Obter um produto por ID", description = "Retorna um único produto baseado no ID.")
    public ResponseEntity<ProdutoDto> getProdutoById(@PathVariable Long id) {
        return produtoService.findById(id)
                .map(produto -> ResponseEntity.ok(convertToDtoWithLinks(produto)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/buscar")
    @Operation(summary = "Buscar produtos por nome", description = "Retorna uma lista de produtos que correspondem ao nome fornecido.")
    public List<ProdutoDto> getProdutosByNome(@RequestParam String nome) {
        return produtoService.findByNome(nome).stream()
                .map(this::convertToDtoWithLinks)
                .collect(Collectors.toList());
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping
    @Operation(summary = "Criar um novo produto", description = "Cria um novo produto e retorna o produto criado.")
    public ProdutoDto createProduto(@Valid @RequestBody ProdutoDto produtoDto) {
        Produto produto = convertToEntity(produtoDto);
        return convertToDtoWithLinks(produtoService.save(produto));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um produto", description = "Atualiza um produto existente com base no ID fornecido.")
    public ResponseEntity<ProdutoDto> updateProduto(@PathVariable Long id, @Valid @RequestBody ProdutoDto produtoDto) {
        return produtoService.findById(id)
                .map(produtoExistente -> {
                    Produto produto = convertToEntity(produtoDto);
                    produto.setId(id);
                    return ResponseEntity.ok(convertToDtoWithLinks(produtoService.save(produto)));
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir um produto", description = "Exclui um produto existente com base no ID fornecido.")
    public ResponseEntity<?> deleteProduto(@PathVariable Long id) {
        return produtoService.findById(id)
                .map(produto -> {
                    produtoService.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    private ProdutoDto convertToDtoWithLinks(Produto produto) {
        ProdutoDto dto = modelMapper.map(produto, ProdutoDto.class);

        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProdutoController.class).getProdutoById(produto.getId())).withSelfRel();
        Link allLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProdutoController.class).getAllProdutos()).withRel("allProdutos");
        dto.add(selfLink);
        dto.add(allLink);

        return dto;
    }

    private ProdutoDto convertToDto(Produto produto) {
        return modelMapper.map(produto, ProdutoDto.class);
    }

    private Produto convertToEntity(ProdutoDto produtoDto) {
        return modelMapper.map(produtoDto, Produto.class);
    }
}