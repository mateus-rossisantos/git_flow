package com.example.animais.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "animal")
public class Animal {
    @Id
    private String id;
    private String name;
    private String breed;

    public Animal(String name, String breed) {
        this.name = name;
        this.breed = breed;
    }


}
