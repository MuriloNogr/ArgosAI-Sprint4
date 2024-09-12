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

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/produtos")
@Validated
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ModelMapper modelMapper;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public List<ProdutoDto> getAllProdutos() {
        return produtoService.findAll().stream()
                .map(this::convertToDtoWithLinks)
                .collect(Collectors.toList());
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDto> getProdutoById(@PathVariable Long id) {
        return produtoService.findById(id)
                .map(produto -> ResponseEntity.ok(convertToDtoWithLinks(produto)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/buscar")
    public List<ProdutoDto> getProdutosByNome(@RequestParam String nome) {
        return produtoService.findByNome(nome).stream()
                .map(this::convertToDtoWithLinks)
                .collect(Collectors.toList());
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping
    public ProdutoDto createProduto(@Valid @RequestBody ProdutoDto produtoDto) {
        Produto produto = convertToEntity(produtoDto);
        return convertToDtoWithLinks(produtoService.save(produto));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/{id}")
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
