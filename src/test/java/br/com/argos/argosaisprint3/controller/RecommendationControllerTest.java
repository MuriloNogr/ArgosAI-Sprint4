package br.com.argos.argosaisprint3.controller;

import br.com.argos.argosaisprint3.controller.RecommendationController;
import br.com.argos.argosaisprint3.model.Recommendation;
import br.com.argos.argosaisprint3.service.OpenAIRecommendationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RecommendationControllerTest {

    @Mock
    private OpenAIRecommendationService recommendationService;

    @Mock
    private Model model;

    @InjectMocks
    private RecommendationController recommendationController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRecommendProducts() throws IOException {
        Recommendation recommendation = new Recommendation("Produto Teste", "Descrição Teste", 99.99, "imagem.jpg");
        when(recommendationService.getProductRecommendations(25, "masculino"))
                .thenReturn(Collections.singletonList(recommendation));

        String viewName = recommendationController.recommendProducts(25, "masculino", model);

        verify(model, times(1)).addAttribute(eq("recommendations"), anyList());
        assertEquals("recommendations", viewName);
    }
}
