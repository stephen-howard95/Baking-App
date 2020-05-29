package com.example.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


public class RecipeDetailsFragment extends Fragment {

    private IngredientsAdapter mIngredientsAdapter;
    private StepsAdapter mStepsAdapter;
    private Recipe mRecipe;

    public RecipeDetailsFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_detail_fragment, container, false);

        TextView recipeNameTextView = rootView.findViewById(R.id.recipe_name_text_view);
        SpannableString content = new SpannableString(mRecipe.getName());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        recipeNameTextView.setText(content);

        mIngredientsAdapter = new IngredientsAdapter(getContext(), mRecipe.getIngredients());
        ListView ingredientsListView = rootView.findViewById(R.id.ingredients_list);
        ingredientsListView.setAdapter(mIngredientsAdapter);

        mStepsAdapter = new StepsAdapter(getContext(), mRecipe.getSteps());
        ListView stepsListView = rootView.findViewById(R.id.steps_list);
        stepsListView.setAdapter(mStepsAdapter);
        stepsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Recipe recipe = mRecipe;
                if(DetailActivity.twoPane){
                    StepDetailFragment stepDetailFragment = new StepDetailFragment();
                    FragmentManager fragmentManager = getFragmentManager();

                    stepDetailFragment.setRecipe(mRecipe);
                    stepDetailFragment.setStepIndex(position);

                    fragmentManager.beginTransaction()
                            .replace(R.id.step_details, stepDetailFragment)
                            .commit();
                }else{
                    launchStepDetailsActivity(position, recipe);
                }
            }
        });
        return rootView;
    }
    private void launchStepDetailsActivity(int stepIndex, Recipe recipe){
        Intent intent = new Intent(getContext(), StepDetailActivity.class);
        intent.putExtra(StepDetailActivity.STEP_INDEX, stepIndex);
        intent.putExtra(StepDetailActivity.RECIPE, recipe);
        startActivity(intent);
    }

    public void setRecipe(Recipe recipe){
        mRecipe = recipe;
    }
}
