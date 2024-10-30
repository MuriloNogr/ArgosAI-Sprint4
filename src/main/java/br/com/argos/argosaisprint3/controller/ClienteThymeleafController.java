package br.com.argos.argosaisprint3.controller;

import br.com.argos.argosaisprint3.exception.ResourceNotFoundException;
import br.com.argos.argosaisprint3.model.Cliente;
import br.com.argos.argosaisprint3.service.ClienteService;
import br.com.argos.argosaisprint3.dto.ClienteDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import jakarta.validation.Valid;
@Controller
@RequestMapping("/clientes")
@Validated
public class ClienteThymeleafController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Listar todos os clientes", description = "Retorna uma lista de todos os clientes")
    @GetMapping("/listar")
    public String listarClientes(Model model) {
        List<ClienteDto> clientes = clienteService.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        model.addAttribute("clientes", clientes);
        return "clientes/listar";
    }

    @Operation(summary = "Exibir o formulário de adição de cliente")
    @GetMapping("/adicionar")
    public String exibirFormularioDeAdicao(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/adicionar";
    }

    @Operation(summary = "Exibir o formulário de edição de cliente")
    @GetMapping("/editar/{id}")
    public String exibirFormularioDeEdicao(@PathVariable Long id, Model model) {
        Cliente cliente = clienteService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
        model.addAttribute("cliente", cliente);
        return "clientes/editar";
    }


    @Operation(summary = "Salvar um cliente", description = "Salva um cliente no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Cliente salvo com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao salvar o cliente")
    })
    @PostMapping
    public String salvarCliente(@Valid @ModelAttribute Cliente cliente, BindingResult result) {
        if (result.hasErrors()) {
            return cliente.getId_cliente() == null ? "clientes/adicionar" : "clientes/editar";
        }
        clienteService.save(cliente);
        return "redirect:/clientes/listar";
    }

    @Operation(summary = "Deletar um cliente", description = "Deleta um cliente do banco de dados")

    @GetMapping("/deletar/{id}")
    public String deletarCliente(@Parameter(description = "ID do cliente a ser deletado") @PathVariable Long id) {
        clienteService.deleteById(id);
        return "redirect:/clientes/listar";
    }

    private ClienteDto convertToDto(Cliente cliente) {
        return modelMapper.map(cliente, ClienteDto.class);
    }

    private Cliente convertToEntity(ClienteDto clienteDto) {
        return modelMapper.map(clienteDto, Cliente.class);
    }
}
