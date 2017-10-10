package com.bakingapp.src;

import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bakingapp.R;
import com.bakingapp.src.adapter.IngredientRecyclerAdapter;
import com.bakingapp.src.model.Ingredient;
import com.bakingapp.src.util.Constants;

import java.util.Arrays;

/**
 * A placeholder fragment containing a simple view.
 */
public class IngredientListActivityFragment extends Fragment {

    private static final String LOG_TAG = IngredientListActivityFragment.class.getSimpleName();
    private Ingredient[] ingredients;
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
            Log.e(LOG_TAG, "Type: " + extras.getParcelableArray(Constants.INGREDIENT_TAG));
            Parcelable[] parcelableArray = extras.getParcelableArray(Constants.INGREDIENT_TAG);
            Log.d(LOG_TAG, "parcelable array : " + parcelableArray.toString());
            ingredients = new Ingredient[parcelableArray.length];
            int i = 0;
            for (Parcelable parcelable : parcelableArray) {
                if (parcelable instanceof Ingredient) {
                    Log.d(LOG_TAG, "parcelable array instance of Ingredient : " + parcelable);
                    ingredients[i++] = (Ingredient) parcelable;
                }
            }
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
        IngredientRecyclerAdapter ingredientRecyclerAdapter = new IngredientRecyclerAdapter(Arrays.asList(ingredients));
        mIngredientRecyclerView.setAdapter(ingredientRecyclerAdapter);
    }
}
