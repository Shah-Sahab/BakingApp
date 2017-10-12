package com.bakingapp;

import android.content.Intent;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.bakingapp.src.RecipeStepDetailsActivity;
import com.bakingapp.src.endpoint.BakingRecipeServiceEndpoint;
import com.bakingapp.src.model.Recipe;
import com.bakingapp.src.util.Constants;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

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
import static android.support.test.espresso.core.deps.guava.base.Preconditions.checkNotNull;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withTagValue;
import static com.bakingapp.MainActivityInstrumentedTest.waitForAsyncTask;
import static com.bakingapp.src.RecipeStepsFragment.BUNDLE_EXTRA_RECIPE_STEP_NUMBER;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;

/**
 * Created by Psych on 10/4/17.
 */
@RunWith(AndroidJUnit4.class)
public class RecipeStepzDetailsInstrumentedTest {

    @Rule
    public ActivityTestRule<RecipeStepDetailsActivity> activityTestRule = new ActivityTestRule<>(RecipeStepDetailsActivity.class, false, false);

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
                intent.putExtra(BUNDLE_EXTRA_RECIPE_STEP_NUMBER, recipes[0].getSteps().get(0));
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
    public void checkMediaPlayer() {
        onView(withId(R.id.playerView)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void clickMediaPlayerPlayButton() {
        onView(withId(R.id.playerView)).check(matches(isCompletelyDisplayed())).perform(click());
    }

    public static Matcher<Object> withStringMatching(final String tagValue) {
        checkNotNull(tagValue);
        return new BoundedMatcher<Object, SimpleExoPlayerView>(SimpleExoPlayerView.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("Matching Simple Exo Player View Tag Value: " + tagValue);
            }

            @Override
            protected boolean matchesSafely(SimpleExoPlayerView item) {
                return item.getTag().toString().equalsIgnoreCase(tagValue);
            }
        };
    }

    public static ViewAction getExoPlayerViewAction() {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Accessing Exo Player Play Button";
            }

            @Override
            public void perform(UiController uiController, View view) {
                SimpleExoPlayerView simpleExoPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.playerView);

                simpleExoPlayerView.getPlayer().setPlayWhenReady(false);

            }
        };
    }
}
