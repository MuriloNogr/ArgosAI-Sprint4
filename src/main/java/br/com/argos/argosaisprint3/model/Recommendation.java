package br.com.argos.argosaisprint3.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Recommendation {
    private String name;
    private String description;
    private Double price;
    private String image;

    public Recommendation(String name, String description, Double price, String image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
    }
}
