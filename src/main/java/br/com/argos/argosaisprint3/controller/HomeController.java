package br.com.argos.argosaisprint3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/index")
    public String index() {
        return "index";  // Certifique-se de que existe um arquivo index.html no diretório de templates
    }
}
