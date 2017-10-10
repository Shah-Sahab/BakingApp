package com.bakingapp.src.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bakingapp.BuildConfig;
import com.bakingapp.R;
import com.bakingapp.src.endpoint.BakingRecipeServiceEndpoint;
import com.bakingapp.src.model.Ingredient;
import com.bakingapp.src.model.Recipe;
import com.bakingapp.src.util.Constants;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Psych on 9/17/17.
 */

public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final String LOG_TAG = ListRemoteViewsFactory.class.getSimpleName();
    Context mContext;
    private List<Ingredient> ingredientList;

    public ListRemoteViewsFactory(Context pContext) {
        mContext = pContext;
    }

    @Override
    public void onCreate() {
        Log.d(LOG_TAG, "onCreate");
    }

    @Override
    public void onDataSetChanged() {
        Log.d(LOG_TAG, "onDataSetChanged");

        List<Recipe> recipeList = getRecipes();
        SharedPreferences preferences = mContext.getSharedPreferences(mContext.getString(R.string.shared_prefs_key), MODE_PRIVATE);
        int recipeId = preferences.getInt(mContext.getString(R.string.recipe_Id), -1);
        for (Recipe recipe : recipeList) {
            if (recipe.getId() == recipeId) {
                ingredientList = recipe.getIngredients();
                break;
            }
        }
    }

    @Override
    public void onDestroy() {
        Log.d(LOG_TAG, "onDestroy");
    }

    @Override
    public int getCount() {
        Log.d(LOG_TAG, "getCount");
        return ingredientList == null ? 0 : ingredientList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Log.d(LOG_TAG, "getViewAt");

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.item_widget);
        rv.setTextViewText(R.id.recipe_ingredient_item, ingredientList.get(position).getIngredient());

        Log.d(LOG_TAG, ingredientList.get(position).getIngredient());

        Intent fillInIntent = new Intent();
        Ingredient[] ingredients = new Ingredient[ingredientList.size()];
        ingredients = ingredientList.toArray(ingredients);
        fillInIntent.putExtra(Constants.INGREDIENT_TAG, ingredients);
        rv.setOnClickFillInIntent(R.id.recipe_ingredient_item, fillInIntent);

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        Log.d(LOG_TAG, "getLoadingView");
        return null;
    }

    @Override
    public int getViewTypeCount() {
        Log.d(LOG_TAG, "getViewTypeCount");
        return 1;
    }

    @Override
    public long getItemId(int position) {
        Log.d(LOG_TAG, "getItemId");
//        return ingredientList.get(position).getId();
        return position;
    }

    @Override
    public boolean hasStableIds() {
        Log.d(LOG_TAG, "hasStableIds");
        return true;
    }

    /**
     * This is only done for practice purposes and an edge case if in case the list gets changed every 30 minutes.
     * Otherwise the developer already is aware and knows that the response can always be saved into the SharedPreferences
     * and that this is not a requirement.
     * If the application is installed on a rooted device. Which basically means that the user can somehow try to open up
     * the SharedPref file and see the contents saved in it.
     *
     * @return List<Recipe>
     */
    private List<Recipe> getRecipes() {

        Recipe[] recipes = null;
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        BakingRecipeServiceEndpoint serviceEndpoint = retrofit.create(BakingRecipeServiceEndpoint.class);
        try {
            recipes = serviceEndpoint.getRecipes().execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Run back on your feet!
            if (recipes == null || recipes.length == 0) {
                return null;
            } else {
                int totalRecipes = recipes.length;
                Log.d(LOG_TAG, "Total # of Recipes = " + String.valueOf(totalRecipes));
                return Arrays.asList(recipes);
            }
        }
    }
}
