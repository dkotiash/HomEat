package com.example.demo.dto;

public class IngredientDto {
    private String name;
    private String quantity;

    public IngredientDto() {}

    public IngredientDto(String name, String quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() { return name; }
    public String getQuantity() { return quantity; }

    public void setName(String name) { this.name = name; }
    public void setQuantity(String quantity) { this.quantity = quantity; }
}