package br.com.argos.argosaisprint3.repository;

import br.com.argos.argosaisprint3.model.Perfil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class PerfilRepositoryTest {

    @Autowired
    private PerfilRepository perfilRepository;

    @Test
    public void testFindByNome() {
        Perfil perfil = new Perfil();
        perfil.setNome("ROLE_USER");
        perfilRepository.save(perfil);

        Perfil foundPerfil = perfilRepository.findByNome("ROLE_USER");
        assertNotNull(foundPerfil);
    }
}
