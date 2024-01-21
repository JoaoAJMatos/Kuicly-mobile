package com.example.kuicly.modelo;

public class Licao {
    private int id;
    private String title,context,video;

    public Licao(int id,String title,String context,String video) {
        this.id = id;
        this.title = title;
        this.context = context;
        this.video = video;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) { this.title = title; }

    public String getContext() {
        return context;
    }

    public void setContext(String context) { this.context = context; }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) { this.video = video; }
}
