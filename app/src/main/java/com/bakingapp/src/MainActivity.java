package com.bakingapp.src;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.bakingapp.R;
import com.bakingapp.src.IdlingResource.SimpleIdlingResource;
import com.bakingapp.src.adapter.BakeryRecyclerAdapter;
import com.bakingapp.src.endpoint.BakingRecipeServiceEndpoint;
import com.bakingapp.src.model.Recipe;
import com.bakingapp.src.util.Constants;
import com.bakingapp.src.util.RecipeCache;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements BakeryRecyclerAdapter.BakeryAdapterClickHandler{

    // http://go.udacity.com/android-baking-app-json
//    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";
    private static final String BASE_URL = "http://go.udacity.com/";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    Recipe[] recipes;
    RecyclerView mRecyclerView;
    private BakeryRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    // Idling resource will be null in production
    @Nullable
    private SimpleIdlingResource mIdlingResource;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getRecipes();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initRecyclerView();
    }

    /**
     * Initialize Recycler View
     */
    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // Check if its a tablet or a phone device
        if (getResources().getBoolean(R.bool.isTablet)) {
            mLayoutManager = new GridLayoutManager(this, 2);
        } else {
            mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        }
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new BakeryRecyclerAdapter(recipes, MainActivity.this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void getRecipes() {
        setIdleState(false);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        BakingRecipeServiceEndpoint serviceEndpoint = retrofit.create(BakingRecipeServiceEndpoint.class);
        serviceEndpoint.getRecipes().enqueue(new Callback<Recipe[]>() {
            @Override
            public void onResponse(Call<Recipe[]> call, Response<Recipe[]> response) {
                int totalRecipes = response.body().length;
                Log.d(LOG_TAG, "Total # of Recipes = " + String.valueOf(totalRecipes));
                // Run back on your feet!
                if (totalRecipes == 0) {
                    return;
                }
                recipes = response.body();
                setIdleState(true);
            }

            @Override
            public void onFailure(Call<Recipe[]> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private void setIdleState(boolean isIdleNow) {
        if (mIdlingResource != null) {
            mIdlingResource.setmIsIdleNow(isIdleNow);
        }
    }

    @Override
    public void onClickListener(Recipe recipe) {
        // Only for running instrumentation test
        RecipeCache.getInstance().setRecipe(recipe);

        Intent intent = new Intent(this, RecipeStepsActivity.class);
        intent.putExtra(Constants.BUNDLE_EXTRA_RECIPE, recipe);
        startActivity(intent);
    }

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }
}
