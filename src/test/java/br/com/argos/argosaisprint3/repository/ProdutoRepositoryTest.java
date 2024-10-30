package br.com.argos.argosaisprint3.repository;

import br.com.argos.argosaisprint3.model.Produto;
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
public class ProdutoRepositoryTest {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Test
    public void testFindByNomeContainingIgnoreCase() {
        Produto produto = new Produto();
        produto.setNome("Produto A");
        produto.setDescricao("Descrição do Produto A");
        produto.setPreco(100.0);
        produto.setQuantidade(10);
        produtoRepository.save(produto);

        List<Produto> result = produtoRepository.findByNomeContainingIgnoreCase("produto");
        assertEquals(1, result.size());
    }
}
