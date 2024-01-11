package com.example.kuicly.modelo;

public class Curso {
    private int id,skill_level;
    private String description,title,capa;
    private float price;

    public Curso(int id,String description, String title,  float price ,int skill_level,String capa) {
        this.id = id;
        this.skill_level = skill_level;
        this.description = description;
        this.title = title;
        this.price = price;
        this.capa = capa;
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

    public void setDescription(String description) { this.description = description; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) { this.title = title; }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) { this.price = price; }
    public String getCapa() {
        return capa;
    }

    public void setCapa(String capa) {
        this.capa = capa;
    }
}
