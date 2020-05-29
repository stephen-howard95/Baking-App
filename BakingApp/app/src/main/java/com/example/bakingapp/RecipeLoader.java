package com.example.bakingapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import org.json.JSONException;

import java.util.List;

public class RecipeLoader extends AsyncTaskLoader<List<Recipe>> {
    private String mUrl;

    public RecipeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Recipe> loadInBackground() {
        if (mUrl == null){
            return null;
        }
        List<Recipe> recipes = null;
        try {
            recipes = JsonUtils.fetchRecipeList(mUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipes;
    }
}
