package com.bakingapp.src;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bakingapp.R;
import com.bakingapp.src.adapter.RecipeStepsAdapter;
import com.bakingapp.src.model.Recipe;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecipeStepsFragment extends Fragment implements RecipeStepsAdapter.StepsClickHandler {

    private final String TAG = RecipeStepsFragment.class.getSimpleName();
    public static final String BUNDLE_EXTRA_RECIPE = "Recipe";
    public static final String BUNDLE_EXTRA_RECIPE_STEP_NUMBER = "RecipeStepNumber";

    RecyclerView mRecyclerView;
    RecipeStepsAdapter recyclerAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public RecipeStepsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_steps, container, false);
        initStepsRecyclerView(rootView);
        return rootView;
    }

    private void initStepsRecyclerView(View rootView) {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_steps);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        // Check if its a tablet or a phone device
        if (getResources().getBoolean(R.bool.isTablet)) {
            // TODO: Multiple Fragments
        }
        mRecyclerView.setLayoutManager(mLayoutManager);
        Recipe recipe = getActivity().getIntent().getParcelableExtra(Intent.EXTRA_REFERRER);
        recyclerAdapter = new RecipeStepsAdapter(recipe, this);
        mRecyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void onClickListener(Recipe recipe, int stepNumber) {
        Log.e(TAG, "Recipe Name = " + recipe.getName());
        Log.e(TAG, "Recipe Step# " + stepNumber);

        Intent intent = new Intent(getActivity(), RecipeStepDetailsActivity.class);
        intent.putExtra(BUNDLE_EXTRA_RECIPE, recipe);
        intent.putExtra(BUNDLE_EXTRA_RECIPE_STEP_NUMBER, stepNumber);
        getContext().startActivity(intent);
    }
}
