package br.com.argos.argosaisprint3.controller;

import br.com.argos.argosaisprint3.dto.ClienteDto;
import br.com.argos.argosaisprint3.model.Cliente;
import br.com.argos.argosaisprint3.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ClienteController clienteController;

    @Test
    public void testGetClienteById() {
        Cliente cliente = new Cliente();
        ClienteDto clienteDto = new ClienteDto();

        when(clienteService.findById(1L)).thenReturn(Optional.of(cliente));
        when(modelMapper.map(cliente, ClienteDto.class)).thenReturn(clienteDto);

        ResponseEntity<ClienteDto> response = clienteController.getClienteById(1L);
        assertEquals(200, response.getStatusCodeValue(), "O código de status deve ser 200 para um cliente encontrado");
    }
}
