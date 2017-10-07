package com.bakingapp.src;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.bakingapp.R;
import com.bakingapp.src.model.Recipe;
import com.bakingapp.src.util.Constants;
import com.bakingapp.src.util.RecipeCache;

/**
 * Created by Psych on 9/6/17.
 */

public class RecipeStepDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Recipe recipe = null;

        if (getIntent().getExtras() != null) {
            recipe = getIntent().getExtras().getParcelable(Constants.BUNDLE_EXTRA_RECIPE);
        } else {
            // For instrumentation
            recipe = RecipeCache.getInstance().getRecipe();
        }
        toolbar.setTitle(recipe.getName());
        setTitle(recipe.getName());
    }
}
