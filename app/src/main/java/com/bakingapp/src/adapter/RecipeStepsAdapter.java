package com.bakingapp.src.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bakingapp.R;
import com.bakingapp.src.model.Recipe;
import com.bakingapp.src.model.Step;

/**
 * Created by Psych on 5/3/17.
 */

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.ViewHolder> {

    public interface StepsClickHandler {
        void onClickListener(Recipe recipe, int stepNumber);
    }

    StepsClickHandler stepsClickHandler;
    private Step[] mStepData;
    Recipe mRecipe;

    public RecipeStepsAdapter(Recipe recipe, StepsClickHandler clickHandler) {
        mRecipe     = recipe;
        mStepData   = new Step[mRecipe.getSteps().size()];
        mStepData   = mRecipe.getSteps().toArray(mStepData);
        stepsClickHandler = clickHandler;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_step_adapter, parent, false);
        return new RecipeStepsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.recipeStepText.setText(mStepData[position].getShortDescription());
    }

    @Override
    public int getItemCount() {
        return mStepData.length;
    }

    public final class ViewHolder extends RecyclerView.ViewHolder {

        public TextView recipeStepText;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setFocusable(true);
            recipeStepText = (TextView) itemView.findViewById(R.id.step_textview);
            itemView.setOnClickListener(clickListener);
        }

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Step step = mStepData[getAdapterPosition()];
                stepsClickHandler.onClickListener(mRecipe, getAdapterPosition());
            }
        };
    }
}
