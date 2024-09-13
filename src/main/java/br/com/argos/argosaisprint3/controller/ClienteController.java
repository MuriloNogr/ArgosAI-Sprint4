package br.com.argos.argosaisprint3.controller;

import br.com.argos.argosaisprint3.dto.ClienteDto;
import br.com.argos.argosaisprint3.model.Cliente;
import br.com.argos.argosaisprint3.service.ClienteService;
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
@RequestMapping("/api/clientes")
@Validated
@Tag(name = "Clientes", description = "APIs para gerenciar clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ModelMapper modelMapper;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    @Operation(summary = "Obter todos os clientes", description = "Retorna uma lista de todos os clientes.")
    public List<ClienteDto> getAllClientes() {
        return clienteService.findAll().stream()
                .map(this::convertToDtoWithLinks)
                .collect(Collectors.toList());
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{id}")
    @Operation(summary = "Obter um cliente por ID", description = "Retorna um único cliente baseado no ID.")
    public ResponseEntity<ClienteDto> getClienteById(@PathVariable Long id) {
        return clienteService.findById(id)
                .map(cliente -> ResponseEntity.ok(convertToDtoWithLinks(cliente)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/buscar")
    @Operation(summary = "Buscar clientes por nome", description = "Retorna uma lista de clientes que correspondem ao nome fornecido.")
    public List<ClienteDto> getClientesByNome(@RequestParam String nome) {
        return clienteService.findByNome(nome).stream()
                .map(this::convertToDtoWithLinks)
                .collect(Collectors.toList());
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping
    @Operation(summary = "Criar um novo cliente", description = "Cria um novo cliente e retorna o cliente criado.")
    public ClienteDto createCliente(@Valid @RequestBody ClienteDto clienteDto) {
        Cliente cliente = convertToEntity(clienteDto);
        return convertToDtoWithLinks(clienteService.save(cliente));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um cliente", description = "Atualiza um cliente existente com base no ID fornecido.")
    public ResponseEntity<ClienteDto> updateCliente(@PathVariable Long id, @Valid @RequestBody ClienteDto clienteDto) {
        return clienteService.findById(id)
                .map(clienteExistente -> {
                    Cliente cliente = convertToEntity(clienteDto);
                    cliente.setId_cliente(id);
                    return ResponseEntity.ok(convertToDtoWithLinks(clienteService.save(cliente)));
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir um cliente", description = "Exclui um cliente existente com base no ID fornecido.")
    public ResponseEntity<?> deleteCliente(@PathVariable Long id) {
        return clienteService.findById(id)
                .map(cliente -> {
                    clienteService.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    private ClienteDto convertToDtoWithLinks(Cliente cliente) {
        ClienteDto dto = modelMapper.map(cliente, ClienteDto.class);

        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClienteController.class).getClienteById(cliente.getId_cliente())).withSelfRel();
        Link allLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClienteController.class).getAllClientes()).withRel("allClientes");
        dto.add(selfLink);
        dto.add(allLink);

        return dto;
    }

    private ClienteDto convertToDto(Cliente cliente) {
        return modelMapper.map(cliente, ClienteDto.class);
    }

    private Cliente convertToEntity(ClienteDto clienteDto) {
        return modelMapper.map(clienteDto, Cliente.class);
    }
}