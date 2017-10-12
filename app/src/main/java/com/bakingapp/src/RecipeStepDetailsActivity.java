package com.bakingapp.src;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bakingapp.R;
import com.bakingapp.src.model.Recipe;
import com.bakingapp.src.util.Constants;
import com.bakingapp.src.util.RecipeCache;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

/**
 * Created by Psych on 9/6/17.
 */

public class RecipeStepDetailsActivity extends AppCompatActivity {

    View mDecorView;
    Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Recipe recipe;

        if (getIntent().getExtras() != null) {
            recipe = getIntent().getExtras().getParcelable(Constants.BUNDLE_EXTRA_RECIPE);
        } else {
            // For instrumentation
            recipe = RecipeCache.getInstance().getRecipe();
        }
        mToolbar.setTitle(recipe.getName());
        setTitle(recipe.getName());

        // Get the Decor View
        mDecorView = getWindow().getDecorView();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
            mToolbar.setVisibility(View.GONE);
            mDecorView.setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else if (hasFocus && getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT) {
            mToolbar.setVisibility(View.VISIBLE);
        }
    }
}
