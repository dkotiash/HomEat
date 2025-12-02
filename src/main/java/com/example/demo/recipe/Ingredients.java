package com.example.demo.recipe;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Ingredients {
    private String name;
    private String quantity;

    public Ingredients() { }
    public Ingredients(String name, String quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getQuantity() { return quantity; }
    public void setQuantity(String quantity) { this.quantity = quantity; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ingredients that)) return false;
        return Objects.equals(name, that.name)
                && Objects.equals(quantity, that.quantity);
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, quantity);
    }
}
