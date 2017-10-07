package com.bakingapp;

import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.bakingapp.src.RecipeStepsActivity;
import com.bakingapp.src.adapter.RecipeStepsAdapter;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Psych on 10/7/17.
 */
@RunWith(AndroidJUnit4.class)
public class RecipeStepsInstrumentedTest {

    @Rule
    public ActivityTestRule<RecipeStepsActivity> activityTestRule = new ActivityTestRule<>(RecipeStepsActivity.class, false, false);

    @Before
    public void init() {
        activityTestRule.launchActivity(new Intent());
        activityTestRule.getActivity().getSupportFragmentManager().beginTransaction();
    }

    @Test
    public void isRecyclerViewDisplayed() {
        onView(withId(R.id.recycler_view_steps)).check(matches(isDisplayed()));
        onView(withId(R.id.recycler_view_steps)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void recyclerViewStepTest() {
        onView(withId(R.id.recycler_view_steps)).perform(RecyclerViewActions.actionOnHolderItem(withRecipeStep("Recipe Introduction"), click()));
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
