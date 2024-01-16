package com.example.kuicly.modelo;

public class Fatura {
    private int id,user_id,iva;
    private String data;
    private float total_price,totaliva,subtotal;


    public Fatura(int id, String data, float total_price, int user_id, int iva, float totaliva, float subtotal) {
        this.id = id;
        this.data = data;
        this.total_price = total_price;
        this.user_id = user_id;
        this.iva = iva;
        this.totaliva = totaliva;
        this.subtotal = subtotal;

    }

    public int getId() {
        return id;
    }
    public void setId(int id) { this.id = id; }
    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) { this.user_id = user_id; }
    public int getIva_id() {
        return iva;
    }
    public void setIva_id(int iva) { this.iva = iva; }
    public String getData() {
        return data;
    }
    public void setData(String data) { this.data = data; }
    public float getTotal_price() {
        return total_price;
    }
    public void setTotal_price(float total_price) { this.total_price = total_price; }
    public float getTotaliva() {
        return totaliva;
    }
    public void setTotaliva(float totaliva) { this.totaliva = totaliva; }
    public float getSubtotal() {
        return subtotal;
    }
    public void setSubtotal(float subtotal) { this.subtotal = subtotal; }









}
