package br.com.argos.argosaisprint3.controller;

import br.com.argos.argosaisprint3.exception.ResourceNotFoundException;
import br.com.argos.argosaisprint3.model.Cliente;
import br.com.argos.argosaisprint3.service.ClienteService;
import br.com.argos.argosaisprint3.dto.ClienteDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/clientes")
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

    @Operation(summary = "Exibir o formulário de adição de cliente", description = "Exibe o formulário para adicionar um novo cliente")
    @GetMapping("/adicionar")
    public String exibirFormularioDeAdicao(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/form";
    }

    @Operation(summary = "Exibir o formulário de edição de cliente", description = "Exibe o formulário para editar um cliente existente")
    @Parameter(name = "id_cliente", description = "ID do cliente a ser editado", required = true)
    @GetMapping("/editar/{id_cliente}")
    public String exibirFormularioDeEdicao(@PathVariable("id_cliente") Long id_cliente, Model model) {
        Cliente cliente = clienteService.findById(id_cliente)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
        model.addAttribute("cliente", cliente);
        return "clientes/form";
    }

    @Operation(summary = "Salvar um cliente", description = "Salva um novo cliente ou atualiza um cliente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Redireciona para a lista de clientes após salvar com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação")
    })
    @PostMapping("/salvar")
    public String salvarCliente(@Valid @ModelAttribute Cliente cliente, BindingResult result) {
        if (result.hasErrors()) {
            return "clientes/form";
        }
        clienteService.save(cliente);
        return "redirect:/clientes/listar";
    }

    private ClienteDto convertToDto(Cliente cliente) {
        return modelMapper.map(cliente, ClienteDto.class);
    }

    private Cliente convertToEntity(ClienteDto clienteDto) {
        return modelMapper.map(clienteDto, Cliente.class);
    }
}
