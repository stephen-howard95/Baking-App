package com.example.bakingapp;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Ingredient implements Serializable {
    private double quantity;
    private String measure;
    private String ingredient;

    public Ingredient(double quantity, String measure, String ingredient){
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public double getQuantity(){
        return quantity;
    }
    public String getMeasure(){
        return measure;
    }
    public String getIngredient(){
        return ingredient;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder ingredientString = new StringBuilder(ingredient)
                .append(": ")
                .append(quantity)
                .append(" ")
                .append(measure);
        return ingredientString.toString();
    }
}
