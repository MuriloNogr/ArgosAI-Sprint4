package br.com.argos.argosaisprint3.controller;

import br.com.argos.argosaisprint3.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Locale;

@Controller
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    public String showContactForm(Model model) {
        return "contact";
    }

    @PostMapping("/sendMessage")
    public String sendMessage(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("message") String message,
            Model model,
            Locale locale) {

        emailService.sendEmail(name, email, message);

        String confirmationMessage = messageSource.getMessage("confirmation.message", null, locale);
        model.addAttribute("confirmationMessage", confirmationMessage);

        return "contact";
    }
}
