package com.example.bakingapp;

import java.io.Serializable;
import java.util.ArrayList;

public class Recipe implements Serializable {
    private int id;
    private String name;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Step> steps;
    private int servings;
    private String image;

    public Recipe(){

    }

    public Recipe(int id, String name, ArrayList<Ingredient> ingredients, ArrayList<Step> steps, int servings, String image){
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public ArrayList<Ingredient> getIngredients(){
        return ingredients;
    }
    public ArrayList<Step> getSteps(){
        return steps;
    }
    public int getServings(){
        return servings;
    }
    public String getImage(){
        return image;
    }
}
