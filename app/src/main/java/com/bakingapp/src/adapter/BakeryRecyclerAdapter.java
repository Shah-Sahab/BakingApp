package com.bakingapp.src.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bakingapp.R;
import com.bakingapp.src.model.Recipe;

/**
 * Created by Psych on 4/29/17.
 */

public class BakeryRecyclerAdapter extends RecyclerView.Adapter<BakeryRecyclerAdapter.ViewHolder> {

    private Recipe[] mRecipeData;

    public BakeryRecyclerAdapter(Recipe[] recipes) {
        mRecipeData = recipes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adapter_recipe, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.recipeNameTextView.setText(mRecipeData[position].getName());
    }

    @Override
    public int getItemCount() {
        return mRecipeData.length;
    }

    /**
     * I don't want anyone extending this class and so hence the keyword final
     */
    public static final class ViewHolder extends RecyclerView.ViewHolder {

        public TextView recipeNameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            recipeNameTextView = (TextView) itemView.findViewById(R.id.recipe_name_text);
        }
    }

}
