package com.example.bakingapp;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class StepDetailActivity extends AppCompatActivity {

    public static final String STEP_INDEX = "step_index";
    public static final String RECIPE = "recipe";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_detail_fragment);

        Recipe recipe = (Recipe) getIntent().getExtras().getSerializable(RECIPE);
        final int stepIndex = (int) getIntent().getExtras().getSerializable(STEP_INDEX);

        StepDetailFragment stepDetailFragment = new StepDetailFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();

        stepDetailFragment.setRecipe(recipe);
        stepDetailFragment.setStepIndex(stepIndex);

        fragmentManager.beginTransaction()
                .add(R.id.step_details, stepDetailFragment)
                .commit();
    }
}
