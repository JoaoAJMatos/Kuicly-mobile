package com.example.kuicly.modelo;

public class Carrinho {
    private int id,user_id;
    private float total;

    public Carrinho(int id,float total, int user_id) {
        this.id = id;
        this.user_id = user_id;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

}
