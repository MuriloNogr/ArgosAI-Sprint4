package br.com.argos.argosaisprint3.controller;

import br.com.argos.argosaisprint3.exception.ResourceNotFoundException;
import br.com.argos.argosaisprint3.model.Produto;
import br.com.argos.argosaisprint3.service.ProdutoService;
import br.com.argos.argosaisprint3.dto.ProdutoDto;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/produtos")
@Validated
public class ProdutoThymeleafController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Listar todos os produtos", description = "Retorna uma lista de todos os produtos")
    @GetMapping("/listar")
    public String listarProdutos(Model model) {
        List<ProdutoDto> produtos = produtoService.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        model.addAttribute("produtos", produtos);
        return "produtos/listar";
    }

    @Operation(summary = "Exibir o formulário de adição de produto", description = "Exibe o formulário para adicionar um novo produto")
    @GetMapping("/adicionar")
    public String exibirFormularioDeAdicao(Model model) {
        model.addAttribute("produto", new Produto());
        return "produtos/form";
    }

    @Operation(summary = "Exibir o formulário de edição de produto", description = "Exibe o formulário para editar um produto existente")
    @Parameter(name = "id", description = "ID do produto a ser editado", required = true)
    @GetMapping("/editar/{id}")
    public String exibirFormularioDeEdicao(@PathVariable Long id, Model model) {
        Produto produto = produtoService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
        model.addAttribute("produto", produto);
        return "produtos/form";
    }

    @Operation(summary = "Salvar um produto", description = "Salva um novo produto ou atualiza um produto existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Redireciona para a lista de produtos após salvar com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação")
    })
    @PostMapping("/salvar")
    public String salvarProduto(@Valid @ModelAttribute Produto produto, BindingResult result) {
        if (result.hasErrors()) {
            return "produtos/form";
        }
        produtoService.save(produto);
        return "redirect:/produtos/listar";
    }

    @Operation(summary = "Deletar um produto", description = "Deleta um produto existente pelo ID")
    @Parameter(name = "id", description = "ID do produto a ser deletado", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Redireciona para a lista de produtos após deletar com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @GetMapping("/deletar/{id}")
    public String deletarProduto(@PathVariable Long id) {
        produtoService.deleteById(id);
        return "redirect:/produtos/listar";
    }

    private ProdutoDto convertToDto(Produto produto) {
        return modelMapper.map(produto, ProdutoDto.class);
    }

    private Produto convertToEntity(ProdutoDto produtoDto) {
        return modelMapper.map(produtoDto, Produto.class);
    }
}
