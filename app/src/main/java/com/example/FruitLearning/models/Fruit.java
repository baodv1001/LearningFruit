package com.example.FruitLearning.models;

public class Fruit {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String name;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String id;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

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
