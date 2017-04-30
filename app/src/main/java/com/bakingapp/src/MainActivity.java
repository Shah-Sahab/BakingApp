package com.bakingapp.src;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bakingapp.R;
import com.bakingapp.src.endpoint.BakingRecipeServiceEndpoint;
import com.bakingapp.src.model.Recipe;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.androidannotations.annotations.ViewById;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getRecipes();
    }

    private void getRecipes() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        BakingRecipeServiceEndpoint serviceEndpoint = retrofit.create(BakingRecipeServiceEndpoint.class);
        serviceEndpoint.getRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                Log.d(LOG_TAG, String.valueOf(response.body().size()));
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
