package com.bakingapp.src;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.bakingapp.R;
import com.bakingapp.src.model.Recipe;

/**
 * Created by Psych on 9/6/17.
 */

public class RecipeStepDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
//
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        Recipe recipe = getIntent().getExtras().getParcelable(RecipeStepsFragment.BUNDLE_EXTRA_RECIPE);
//        toolbar.setTitle(recipe.getName());
//        setTitle(recipe.getName());
    }
}
