package com.bakingapp.src.util;

import com.bakingapp.src.model.Recipe;

/**
 * Created by Psych on 9/6/17.
 */

public enum RecipeCache {
    INSTANCE;

    private Recipe recipe;
    private int recipeStep;

    public static RecipeCache getInstance() {
        return INSTANCE;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public int getRecipeStep() {
        return recipeStep;
    }

    public void setRecipeStep(int recipeStep) {
        this.recipeStep = recipeStep;
    }
}
