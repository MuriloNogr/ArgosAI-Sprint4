package br.com.argos.argosaisprint3.repository;

import br.com.argos.argosaisprint3.model.Cliente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    public void testFindByNomeContainingIgnoreCase() {
        Cliente cliente = new Cliente();
        cliente.setNome("Carlos");
        cliente.setCpf("12345678901");
        cliente.setEmail("carlos@example.com");
        clienteRepository.save(cliente);

        List<Cliente> result = clienteRepository.findByNomeContainingIgnoreCase("carlos");
        assertEquals(1, result.size());
    }
}
