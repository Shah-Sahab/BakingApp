package com.bakingapp.src.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bakingapp.R;

/**
 * Created by Psych on 5/3/17.
 */

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.ViewHolder> {

    public RecipeStepsAdapter() {

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public final class ViewHolder extends RecyclerView.ViewHolder {

        public TextView recipeStepText;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setFocusable(true);
            recipeStepText = (TextView) itemView.findViewById(R.id.recipe_name_text);
            itemView.setOnClickListener(clickListener);
        }

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Recipe recipe = mRecipeData[getAdapterPosition()];
//                mClickHandler.onClickListener(recipe);
//                Log.e("BakeryRecyclerAdapter", "Recipe= " + recipe.getName());
            }
        };
    }
}
