package com.example.kuicly.modelo;

public class Curso {
    private int id,skill_level;
    private String description,title;
    private float price;

    public Curso(int id,String description, String title,  float price ,int skill_level) {
        this.id = id;
        this.skill_level = skill_level;
        this.description = description;
        this.title = title;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public int getSkill_level() {
        return skill_level;
    }

    public void setSkill_level(int skill_level) {
        this.skill_level = skill_level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String descripcion) { this.description = descripcion; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) { this.title = title; }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) { this.price = price; }
}
