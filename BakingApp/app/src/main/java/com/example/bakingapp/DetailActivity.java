package com.example.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class DetailActivity extends AppCompatActivity {

    public static final String RECIPE = "recipe";
    public static Recipe recipe;

    public static boolean twoPane;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_whole_recipe);

        recipe = (Recipe) getIntent().getExtras().getSerializable(RECIPE);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));
        RecipeWidgetProvider.updateRecipeWidget(this, appWidgetManager, appWidgetIds);

        RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();

        recipeDetailsFragment.setRecipe(recipe);

        fragmentManager.beginTransaction()
                .add(R.id.recipe_detail_ingredients_steps_lists, recipeDetailsFragment)
                .commit();

        if(findViewById(R.id.step_details) != null){
            twoPane = true;
            StepDetailFragment stepDetailFragment = new StepDetailFragment();

            stepDetailFragment.setRecipe(recipe);

            fragmentManager.beginTransaction()
                    .add(R.id.step_details, stepDetailFragment)
                    .commit();
        } else{
            twoPane = false;
        }
    }
}