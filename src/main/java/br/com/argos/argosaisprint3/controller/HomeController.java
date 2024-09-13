package br.com.argos.argosaisprint3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@Tag(name = "Home", description = "Controlador de página inicial")
public class HomeController {

    @GetMapping("/")
    @Operation(summary = "Página Inicial", description = "Retorna a página inicial do sistema.")
    public String home() {
        return "index";
    }
}