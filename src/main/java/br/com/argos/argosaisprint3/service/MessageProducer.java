package br.com.argos.argosaisprint3.service;

import br.com.argos.argosaisprint3.dto.ContactFormDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(ContactFormDto contactFormDto) {
        rabbitTemplate.convertAndSend("contact.queue", contactFormDto);
    }
}
