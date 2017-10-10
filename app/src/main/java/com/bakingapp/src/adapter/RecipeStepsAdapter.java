package com.bakingapp.src.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bakingapp.R;
import com.bakingapp.src.model.Ingredient;
import com.bakingapp.src.model.Recipe;
import com.bakingapp.src.model.Step;

/**
 * Created by Psych on 5/3/17.
 */

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.ViewHolder> {

    private static final int VIEW_TYPE_INGREDIENTS = 0;
    private static final int VIEW_TYPE_STEPS = 1;

    public interface StepsClickHandler {
        void onClickListener(Recipe recipe, int stepNumber);
    }

    StepsClickHandler stepsClickHandler;
    private Step[] mStepData;
    Recipe mRecipe;

    int totalIngredients = -1;

    public RecipeStepsAdapter(Recipe recipe, StepsClickHandler clickHandler) {
        mRecipe           = recipe;
        mStepData         = new Step[mRecipe.getSteps().size()];
        mStepData         = mRecipe.getSteps().toArray(mStepData);
        totalIngredients  = mRecipe.getIngredients().size() - 1;
        stepsClickHandler = clickHandler;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_INGREDIENTS : VIEW_TYPE_STEPS;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layoutId = -1;

        switch (viewType) {
            case VIEW_TYPE_INGREDIENTS:
                layoutId = R.layout.item_step_adapter_ingredients;
                break;
            case VIEW_TYPE_STEPS:
                layoutId = R.layout.item_step_adapter;
                break;
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new RecipeStepsAdapter.ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_STEPS:
                holder.recipeStepText.setText(mStepData[position].getShortDescription());
                break;
            case VIEW_TYPE_INGREDIENTS:
                int i = 0;
                StringBuilder builder = new StringBuilder();
                for (Ingredient ingredient : mRecipe.getIngredients()) {
                    builder.append(ingredient.getIngredient()).append(i++ == totalIngredients ? "" : ", ");
                }
                holder.recipeStepText.setText(builder.toString());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mStepData.length;
    }

    public final class ViewHolder extends RecyclerView.ViewHolder {

        public TextView recipeStepText;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            itemView.setFocusable(true);

            if (viewType == VIEW_TYPE_STEPS) {
                recipeStepText = (TextView) itemView.findViewById(R.id.step_textview);
                itemView.setOnClickListener(clickListener);
            } else if (viewType == VIEW_TYPE_INGREDIENTS) {
                recipeStepText = (TextView) itemView.findViewById(R.id.ingredients_textview);
            }
        }

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stepsClickHandler.onClickListener(mRecipe, getAdapterPosition());
            }
        };
    }
}
