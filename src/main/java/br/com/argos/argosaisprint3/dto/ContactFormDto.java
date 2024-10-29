package br.com.argos.argosaisprint3.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ContactFormDto {

    private String name;
    private String email;
    private String subject;
    private String message;

}
