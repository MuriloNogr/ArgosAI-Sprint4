package br.com.argos.argosaisprint3.controller;

import br.com.argos.argosaisprint3.exception.ResourceNotFoundException;
import br.com.argos.argosaisprint3.model.Produto;
import br.com.argos.argosaisprint3.service.ProdutoService;
import br.com.argos.argosaisprint3.dto.ProdutoDto;
import jakarta.validation.Valid;
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

    @GetMapping("/listar")
    public String listarProdutos(Model model) {
        List<ProdutoDto> produtos = produtoService.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        model.addAttribute("produtos", produtos);
        return "produtos/listar"; // Nome correto do template Thymeleaf
    }

    @GetMapping("/adicionar")
    public String exibirFormularioDeAdicao(Model model) {
        model.addAttribute("produto", new Produto());
        return "produtos/form"; // Nome correto do template Thymeleaf
    }

    @GetMapping("/editar/{id}")
    public String exibirFormularioDeEdicao(@PathVariable Long id, Model model) {
        Produto produto = produtoService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
        model.addAttribute("produto", produto);
        return "produtos/form"; // Nome correto do template Thymeleaf
    }

    @PostMapping("/salvar")
    public String salvarProduto(@Valid @ModelAttribute Produto produto, BindingResult result) {
        if (result.hasErrors()) {
            return "produtos/form";
        }
        produtoService.save(produto);
        return "redirect:/produtos/listar";
    }

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