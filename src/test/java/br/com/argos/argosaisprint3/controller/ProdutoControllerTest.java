package br.com.argos.argosaisprint3.controller;

import br.com.argos.argosaisprint3.dto.ProdutoDto;
import br.com.argos.argosaisprint3.model.Produto;
import br.com.argos.argosaisprint3.service.ProdutoService;
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
public class ProdutoControllerTest {

    @Mock
    private ProdutoService produtoService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProdutoController produtoController;

    @Test
    public void testGetProdutoById() {
        Produto produto = new Produto();
        ProdutoDto produtoDto = new ProdutoDto();
        when(produtoService.findById(1L)).thenReturn(Optional.of(produto));
        when(modelMapper.map(produto, ProdutoDto.class)).thenReturn(produtoDto);

        ResponseEntity<ProdutoDto> response = produtoController.getProdutoById(1L);
        assertEquals(200, response.getStatusCodeValue());
    }
}
