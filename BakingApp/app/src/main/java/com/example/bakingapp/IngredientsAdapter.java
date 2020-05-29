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

public class IngredientsAdapter extends ArrayAdapter<Ingredient> {
    public IngredientsAdapter(@NonNull Context context, ArrayList<Ingredient> resource) {
        super(context, 0, resource);
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View ingredientView = convertView;
        if(ingredientView == null){
            ingredientView = LayoutInflater.from(getContext()).inflate(R.layout.ingredients_list_item, parent, false);
        }
        Ingredient ingredient = getItem(position);
        TextView ingredientTextView = ingredientView.findViewById(R.id.ingredient);
        ingredientTextView.setText(ingredient.toString());
        return ingredientView;
    }
}
