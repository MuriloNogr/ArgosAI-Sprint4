package br.com.argos.argosaisprint3.service;

import br.com.argos.argosaisprint3.model.Produto;
import br.com.argos.argosaisprint3.repository.ProdutoRepository;
import br.com.argos.argosaisprint3.service.ProdutoService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    public ProdutoServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        // Configura uma lista simulada de produtos
        Produto produto = new Produto();
        produto.setNome("Produto Teste");
        produto.setDescricao("Descrição Teste");
        produto.setPreco(100.0);

        when(produtoRepository.findAll()).thenReturn(Arrays.asList(produto));

        // Testa o método findAll
        List<Produto> produtos = produtoService.findAll();
        assertEquals(1, produtos.size());
        assertEquals("Produto Teste", produtos.get(0).getNome());
    }

    @Test
    public void testFindById() {
        // Configura um produto simulado
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Produto Teste");

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        // Testa o método findById
        Optional<Produto> foundProduto = produtoService.findById(1L);
        assertTrue(foundProduto.isPresent());
        assertEquals("Produto Teste", foundProduto.get().getNome());
    }
}
