package com.bakingapp;

import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.bakingapp.src.RecipeStepsActivity;
import com.bakingapp.src.adapter.RecipeStepsAdapter;
import com.bakingapp.src.endpoint.BakingRecipeServiceEndpoint;
import com.bakingapp.src.model.Recipe;
import com.bakingapp.src.util.Constants;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.bakingapp.MainActivityInstrumentedTest.waitForAsyncTask;

/**
 * Created by Psych on 10/7/17.
 */
@RunWith(AndroidJUnit4.class)
public class RecipeStepsInstrumentedTest {

    @Rule
    public ActivityTestRule<RecipeStepsActivity> activityTestRule = new ActivityTestRule<>(RecipeStepsActivity.class, false, false);

    @Before
    public void init() {

        Recipe[] recipes = null;
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        BakingRecipeServiceEndpoint serviceEndpoint = retrofit.create(BakingRecipeServiceEndpoint.class);
        try {
            recipes = serviceEndpoint.getRecipes().execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Run back on your feet!
            if (recipes == null || recipes.length == 0) {
                return;
            } else {
                Intent intent = new Intent();
                intent.putExtra(Constants.BUNDLE_EXTRA_RECIPE, recipes[0]);
                activityTestRule.launchActivity(intent);
                activityTestRule.getActivity().getSupportFragmentManager().beginTransaction();
            }
        }

        try {
            waitForAsyncTask();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Test
    public void isRecyclerViewDisplayed() {
        // For some reason, recycler id is replaced with fragment id. So have to use it now to run the test.
        onView(withId(R.id.recipe_step_fragment)).check(matches(isDisplayed()));
        onView(withId(R.id.recipe_step_fragment)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void recyclerViewStepTest() {
        // For some reason, recycler id is replaced with fragment id. So have to use it now to run the test.
        onView(withId(R.id.recipe_step_fragment)).perform(RecyclerViewActions.actionOnHolderItem(withRecipeStep("Recipe Introduction"), click()));
    }

    public static Matcher<RecyclerView.ViewHolder> withRecipeStep(final String recipeStep) {
        return new BoundedMatcher<RecyclerView.ViewHolder, RecipeStepsAdapter.ViewHolder>(RecipeStepsAdapter.ViewHolder.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("View Holder with Recipe Step: " + recipeStep);
            }

            @Override
            protected boolean matchesSafely(RecipeStepsAdapter.ViewHolder item) {
                return item.recipeStepText.getText().toString().equalsIgnoreCase(recipeStep);
            }
        };
    }

}
