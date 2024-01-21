package com.example.kuicly.modelo;

public class CarrinhoItens {

    private int id,carrinho_id,curso_id;

    private String title,capa;
    private float price;

    public CarrinhoItens(int id,String title,float price,String capa,int carrinho_id, int curso_id) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.capa = capa;
        this.carrinho_id = carrinho_id;
        this.curso_id = curso_id;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getCarrinho_id() {
            return carrinho_id;
        }
        public void setCarrinho_id(int carrinho_id) {
            this.carrinho_id = carrinho_id;
        }
        public int getCurso_id() {
            return curso_id;
        }

        public void setCurso_id(int curso_id) {
            this.curso_id = curso_id;
        }
}
