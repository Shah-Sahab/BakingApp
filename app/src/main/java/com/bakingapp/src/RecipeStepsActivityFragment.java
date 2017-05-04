package com.bakingapp.src;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bakingapp.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecipeStepsActivityFragment extends Fragment {

    RecyclerView mRecyclerView;

    public RecipeStepsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_steps, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_steps);
        return rootView;
    }


}
