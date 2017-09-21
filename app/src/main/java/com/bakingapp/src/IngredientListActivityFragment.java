package com.bakingapp.src;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bakingapp.R;
import com.bakingapp.src.adapter.IngredientRecyclerAdapter;
import com.bakingapp.src.model.Recipe;
import com.bakingapp.src.util.Constants;

/**
 * A placeholder fragment containing a simple view.
 */
public class IngredientListActivityFragment extends Fragment {

    private Recipe recipe;
    private View mRootView;
    private RecyclerView mIngredientRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    public IngredientListActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            recipe = extras.getParcelable(Constants.RECIPE_TAG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_ingredient_list, container, false);
        initRecyclerView();
        return mRootView;
    }

    private void initRecyclerView() {
        mIngredientRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recycler_view_ingredient);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mIngredientRecyclerView.setHasFixedSize(true);

        // Check if its a tablet or a phone device
        if (getResources().getBoolean(R.bool.isTablet)) {
            mLayoutManager = new GridLayoutManager(getContext(), 2);
        } else {
            mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        }
        mIngredientRecyclerView.setLayoutManager(mLayoutManager);
        IngredientRecyclerAdapter ingredientRecyclerAdapter = new IngredientRecyclerAdapter(recipe.getIngredients());
        mIngredientRecyclerView.setAdapter(ingredientRecyclerAdapter);
    }
}
