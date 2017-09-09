package com.bakingapp.src;

import android.content.Intent;
import android.support.annotation.Nullable;
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
import com.bakingapp.src.util.Constants;

import static com.bakingapp.src.util.Constants.BUNDLE_EXTRA_RECIPE;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecipeStepsFragment extends Fragment implements RecipeStepsAdapter.StepsClickHandler {

    Recipe recipe;

    public interface ListItemClickHandler {
        void onClick(int recipeStep);
    }

    private final String TAG = RecipeStepsFragment.class.getSimpleName();

    public static final String BUNDLE_EXTRA_RECIPE_STEP_NUMBER = "RecipeStepNumber";

    RecyclerView mRecyclerView;
    RecipeStepsAdapter recyclerAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ListItemClickHandler mListItemClickHandler;

    public void setListItemClickHandler(ListItemClickHandler mListItemClickHandler) {
        this.mListItemClickHandler = mListItemClickHandler;
    }

    public RecipeStepsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            recipe = extras.getParcelable(Constants.BUNDLE_EXTRA_RECIPE);
        }
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
        mRecyclerView.setLayoutManager(mLayoutManager);
        recyclerAdapter = new RecipeStepsAdapter(recipe, this);
        mRecyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void onClickListener(Recipe recipe, int stepNumber) {
        Log.e(TAG, "Recipe Name = " + recipe.getName());
        Log.e(TAG, "Recipe Step# " + stepNumber);

        // mListItemClickHandler will only be instantiated if the device is a tablet.
        // Therefore if its a tablet, RecipeStepDetailsActivity doesn't need to be started, hence the return
        // Even though we dont need the isTablet check, its been put here just in case, so that if the
        // developer makes a mistake of adding a click listener any way this condition fails
        if (getResources().getBoolean(R.bool.isTablet) && mListItemClickHandler != null) {
            mListItemClickHandler.onClick(stepNumber);
            return;
        }

        Intent intent = new Intent(getActivity(), RecipeStepDetailsActivity.class);
        intent.putExtra(BUNDLE_EXTRA_RECIPE, recipe);
        intent.putExtra(BUNDLE_EXTRA_RECIPE_STEP_NUMBER, stepNumber);
        getContext().startActivity(intent);
    }
}
