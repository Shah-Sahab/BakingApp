package com.bakingapp.src.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bakingapp.R;
import com.bakingapp.src.model.Ingredient;

import java.util.List;

/**
 * Created by Psych on 9/20/17.
 */

public class IngredientRecyclerAdapter extends RecyclerView.Adapter<IngredientRecyclerAdapter.ViewHolder> {

    private List<Ingredient> mIngredientList;

    public IngredientRecyclerAdapter(List<Ingredient> ingredients) {
        mIngredientList = ingredients;
    }

    @Override
    public IngredientRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adapter_recipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientRecyclerAdapter.ViewHolder holder, int position) {
        holder.ingredientTextView.setText(mIngredientList.get(position).getIngredient());
    }

    @Override
    public int getItemCount() {
        return mIngredientList == null ? 0 : mIngredientList.size();
    }

    public final class ViewHolder extends RecyclerView.ViewHolder {

        public TextView ingredientTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setFocusable(true);
            ingredientTextView = (TextView) itemView.findViewById(R.id.recipe_name_text);
        }

    }
}
