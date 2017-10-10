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
    private BakeryAdapterClickHandler mClickHandler;
    private ItemPositionClickListener mItemPositionClickListener;

    public interface BakeryAdapterClickHandler {
        void onClickListener(Recipe recipe);
    }

    public interface ItemPositionClickListener {
        void onClickListener(int recipeId);
    }

    public BakeryRecyclerAdapter(Recipe[] recipes, BakeryAdapterClickHandler clickHandler) {
        mRecipeData = recipes;
        mClickHandler = clickHandler;
    }

    public BakeryRecyclerAdapter(Recipe[] recipes, ItemPositionClickListener itemPositionClickListener) {
        mRecipeData = recipes;
        mItemPositionClickListener = itemPositionClickListener;
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
    public final class ViewHolder extends RecyclerView.ViewHolder {

        public TextView recipeNameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setFocusable(true);
            recipeNameTextView = (TextView) itemView.findViewById(R.id.recipe_ingredient_item);
            itemView.setOnClickListener(clickListener);
        }

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Recipe recipe = mRecipeData[getAdapterPosition()];
                if (mClickHandler != null) {
                    mClickHandler.onClickListener(recipe);
                }

                if (mItemPositionClickListener != null) {
                    mItemPositionClickListener.onClickListener(mRecipeData[getAdapterPosition()].getId());
                }
            }
        };
    }

}
