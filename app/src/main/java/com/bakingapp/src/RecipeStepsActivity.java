package com.bakingapp.src;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bakingapp.R;

public class RecipeStepsActivity extends AppCompatActivity {

    RecipeStepsFragment recipeStepsFragment;
    RecipeStepDetailsFragment recipeStepDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // If and only if its a tablet
        if (getResources().getBoolean(R.bool.isTablet)) {
            recipeStepsFragment = (RecipeStepsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_recipe_steps_list);
            recipeStepDetailsFragment = (RecipeStepDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_recipe_steps_details);

            RecipeStepsFragment.ListItemClickHandler clickHandler = new RecipeStepsFragment.ListItemClickHandler() {
                @Override
                public void onClick(int recipeStep) {
                    recipeStepDetailsFragment.showRecipeStepDetails(recipeStep);
                }
            };
            recipeStepsFragment.setListItemClickHandler(clickHandler);
        }

    }

}
