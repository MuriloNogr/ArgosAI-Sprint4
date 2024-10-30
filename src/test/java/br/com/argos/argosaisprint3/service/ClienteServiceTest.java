package br.com.argos.argosaisprint3.service;

import br.com.argos.argosaisprint3.model.Cliente;
import br.com.argos.argosaisprint3.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void findById() {
        Cliente cliente = new Cliente();
        cliente.setId_cliente(1L);
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Optional<Cliente> foundCliente = clienteService.findById(1L);
        assertEquals(1L, foundCliente.get().getId_cliente());
    }

    @Test
    void save() {
        Cliente cliente = new Cliente();
        cliente.setNome("Cliente Teste");

        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente savedCliente = clienteService.save(cliente);
        assertEquals("Cliente Teste", savedCliente.getNome());
    }
}
