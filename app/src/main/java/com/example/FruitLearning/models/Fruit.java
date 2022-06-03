package com.example.FruitLearning.models;

public class Fruit {
    public String name;
    public String description;
    public String id;
    public String image;

    public Fruit() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Fruit(String name, String description, String image, String id) {
        this.name = name;
        this.description = description;
        this.description = description;
        this.image = image;
        this.id = id;
    }

}
