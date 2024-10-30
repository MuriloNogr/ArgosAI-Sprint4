package br.com.argos.argosaisprint3.repository;

import br.com.argos.argosaisprint3.model.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void testFindByUsername() {
        Usuario usuario = new Usuario();
        usuario.setUsername("testuser");
        usuario.setSenha("testpassword");
        usuarioRepository.save(usuario);

        Optional<Usuario> foundUser = usuarioRepository.findByUsername("testuser");
        assertTrue(foundUser.isPresent());
    }
}
