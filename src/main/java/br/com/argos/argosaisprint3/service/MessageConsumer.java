package br.com.argos.argosaisprint3.service;

import br.com.argos.argosaisprint3.dto.ContactFormDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {

    private final JavaMailSender mailSender;

    @Autowired
    public MessageConsumer(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @RabbitListener(queues = "contact.queue")
    public void receiveMessage(ContactFormDto contactFormDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("argos.ai.fiap@gmail.com");
        message.setSubject(contactFormDto.getSubject());
        message.setText("Nome: " + contactFormDto.getName() + "\n"
                + "Email: " + contactFormDto.getEmail() + "\n\n"
                + contactFormDto.getMessage());

        mailSender.send(message);
    }
}
