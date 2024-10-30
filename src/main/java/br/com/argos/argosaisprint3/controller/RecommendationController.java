package br.com.argos.argosaisprint3.controller;

import br.com.argos.argosaisprint3.model.Recommendation;
import br.com.argos.argosaisprint3.service.OpenAIRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
public class RecommendationController {

    private final OpenAIRecommendationService recommendationService;

    @Autowired
    public RecommendationController(OpenAIRecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/recommendationForm")
    public String showRecommendationForm() {
        return "recommendationForm";
    }

    @GetMapping("/recommendations")
    public String recommendProducts(@RequestParam int age, @RequestParam String sexo, Model model) {
        try {
            List<Recommendation> recommendations = recommendationService.getProductRecommendations(age, sexo);
            model.addAttribute("recommendations", recommendations);
            return "recommendations";
        } catch (IOException e) {
            model.addAttribute("error", "Erro ao obter recomendações: " + e.getMessage());
            return "recommendations";
        }
    }
}
