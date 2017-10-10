package com.bakingapp.src.widget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.RemoteViews;

import com.bakingapp.R;
import com.bakingapp.src.adapter.BakeryRecyclerAdapter;
import com.bakingapp.src.endpoint.BakingRecipeServiceEndpoint;
import com.bakingapp.src.model.Recipe;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WidgetConfigurationActivity extends AppCompatActivity implements BakeryRecyclerAdapter.ItemPositionClickListener {

    private static final String BASE_URL = "http://go.udacity.com/";
    private static final String LOG_TAG = WidgetConfigurationActivity.class.getSimpleName();

    private int mAppWidgetId;

    RecyclerView mRecyclerView;
    private BakeryRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_configuration);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initRecyclerView();
        getRecipes();
        initAppWidgetId();
    }

    private void initAppWidgetId() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }
    }

    /**
     * Initialize Recycler View
     */
    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // Check if its a tablet or a phone device
        if (getResources().getBoolean(R.bool.isTablet)) {
            mLayoutManager = new GridLayoutManager(this, 2);
        } else {
            mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        }
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void getRecipes() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        BakingRecipeServiceEndpoint serviceEndpoint = retrofit.create(BakingRecipeServiceEndpoint.class);
        serviceEndpoint.getRecipes().enqueue(new Callback<Recipe[]>() {
            @Override
            public void onResponse(Call<Recipe[]> call, Response<Recipe[]> response) {
                int totalRecipes = response.body().length;
                Log.d(LOG_TAG, "Total # of Recipes = " + String.valueOf(totalRecipes));
                // Run back on your feet!
                if (totalRecipes == 0) {
                    return;
                }
                mAdapter = new BakeryRecyclerAdapter(response.body(), WidgetConfigurationActivity.this);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<Recipe[]> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onClickListener(int recipeItemId) {
        SharedPreferences preferences = getSharedPreferences(getString(R.string.shared_prefs_key), MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(getString(R.string.recipe_Id), recipeItemId);
        editor.commit();

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        RemoteViews views = new RemoteViews(getPackageName(), R.layout.recipe_widget);
        appWidgetManager.updateAppWidget(mAppWidgetId, views);
        appWidgetManager.notifyAppWidgetViewDataChanged(mAppWidgetId, R.id.recipe_list);

        Intent broadcastIntent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE, null, this, RecipeWidgetProvider.class);
        broadcastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, new int[] { mAppWidgetId });
        sendBroadcast(broadcastIntent);

        Intent intent = new Intent();
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, intent);
        finish();
    }
}
