package com.bakingapp.src.endpoint;

import com.bakingapp.src.model.Recipe;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Psych on 4/30/17.
 */

public interface BakingRecipeServiceEndpoint {
    @GET("topher/2017/March/58d1537b_baking/baking.json")
    Call<Recipe[]> getRecipes();
}
