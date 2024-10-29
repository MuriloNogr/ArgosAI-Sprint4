package br.com.argos.argosaisprint3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String name, String email, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("argos.ai.fiap@gmail.com"); // Substitua pelo e-mail de destino
        mailMessage.setSubject("Mensagem de Contato: " + name);
        mailMessage.setText("Nome: " + name + "\nE-mail: " + email + "\n\nMensagem:\n" + message);

        mailSender.send(mailMessage);
    }
}
