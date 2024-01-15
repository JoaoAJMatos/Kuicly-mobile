package com.example.kuicly.modelo;

public class FaturaItens {
    private int id,orders_id,courses_id;
    private String title;
    private float price,iva_price;

    public FaturaItens(int id, int orders_id, int courses_id, float price, float iva_price, String title) {
        this.id = id;
        this.orders_id = orders_id;
        this.courses_id = courses_id;
        this.price = price;
        this.iva_price = iva_price;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public int getOrders_id() {
        return orders_id;
    }

    public void setOrders_id(int orders_id) { this.orders_id = orders_id; }

    public int getCourses_id() {
        return courses_id;
    }

    public void setCourses_id(int courses_id) { this.courses_id = courses_id; }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) { this.price = price; }

    public float getIva_price() {
        return iva_price;
    }

    public void setIva_price(float iva_price) { this.iva_price = iva_price; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) { this.title = title; }


}
