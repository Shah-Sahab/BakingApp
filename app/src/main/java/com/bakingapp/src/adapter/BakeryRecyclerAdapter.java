package com.bakingapp.src.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adapter_recipe, parent, false);
        return new ViewHolder(view);
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
            itemView.setFocusable(true);
            recipeNameTextView = (TextView) itemView.findViewById(R.id.recipe_name_text);
            itemView.setOnClickListener(clickListener);
        }

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("BakeryRecyclerAdapter", "Item Clicked");
            }
        };
    }

}
