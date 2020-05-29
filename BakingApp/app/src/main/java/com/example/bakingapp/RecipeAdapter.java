package com.example.bakingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class RecipeAdapter extends ArrayAdapter<Recipe> {

    public RecipeAdapter(@NonNull Context context, ArrayList<Recipe> resource) {
        super(context, 0, resource);
    }
    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View recipeView = convertView;
        if(recipeView == null){
            recipeView = LayoutInflater.from(getContext()).inflate(R.layout.recipe_list_item, parent, false);
        }
        Recipe recipe = getItem(position);
        TextView recipeNameTextView = recipeView.findViewById(R.id.recipe_name);
        recipeNameTextView.setText(recipe.getName());

        return recipeView;
    }
}
