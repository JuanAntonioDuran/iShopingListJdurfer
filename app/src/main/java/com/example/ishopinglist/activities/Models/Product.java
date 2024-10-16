package com.example.ishopinglist.activities.Models;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String name;
    private String quantity;
    private boolean pendiente;
    private boolean Lactosa;
    private boolean gluten;

    public Product(int id, String name, String quantity, boolean pendiente, boolean Lactosa , boolean gluten ) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.pendiente = pendiente;
        this.Lactosa = Lactosa;
        this.gluten = gluten;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String nombre) {
        this.name = nombre;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String cantidad) {
        this.quantity = cantidad;
    }

    public boolean isPendiente() {
        return pendiente; //
    }

    public void setPendiente(boolean pendiente) {
        this.pendiente = pendiente;
    }

    public boolean isLactosa() {
        return Lactosa; //
    }

    public void setLactosa(boolean lactosa) {
        this.Lactosa = lactosa;
    }

    public boolean isGluten() {
        return gluten; //
    }

    public void setGluten(boolean gluten) {
        this.gluten = gluten;
    }




    @Override
    public String toString() {
        return "Product{" +
                "Id='" + id + '\'' +
                ", Nombre='" + name + '\'' +
                ", Cantidad='" + quantity + '\'' + // Corrección: Para una mejor legibilidad
                ", Pendiente=" + pendiente +
                ",Lactosa=" + Lactosa +
                ",Gluten=" + gluten +
                '}';
    }
}





