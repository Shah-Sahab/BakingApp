package com.bakingapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.bakingapp.src.MainActivity;
import com.bakingapp.src.adapter.BakeryRecyclerAdapter;
import com.google.common.truth.Truth;

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
import static com.googlecode.eyesfree.utils.LogUtils.TAG;
import static junit.framework.Assert.assertEquals;


/**
 * Created by Psych on 9/20/17.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class, false, false);

    private IdlingResource mIdlingResource;

    @Before
    public void init() throws Exception {
        activityTestRule.launchActivity(new Intent());
        // downloading the json takes some millisecs.
        // Idling resources is full of nonsense. @Before tagged methods wait for the launch of an activity.
        // What happens when you try to make a webservice call on OnCreate() ? You cannot run the stupid test.
        // Therefore need to use wait for AsyncTask instead.
        try {
            waitForAsyncTask();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        mIdlingResource = activityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }

    public static void waitForAsyncTask() throws Throwable {
        Log.d(TAG, "testAsyncTask entry");

        AsyncTask<String, Void, Integer> task = new AsyncTask<String, Void, Integer>() {

            @Override
            protected Integer doInBackground(String... params) {
                Log.d(TAG, "doInBackground() called with: params = [" + params + "]");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {
                }
                return params.length;
            }

            @Override
            protected void onPostExecute(Integer integer) {
                Log.d(TAG, "onPostExecute() called with: integer = [" + integer + "]");
                assertEquals(3, (int) integer);
                throw new RuntimeException("this should fail the test");
            }
        };
        task.execute("One", "two", "three");
        Espresso.onView(withId(android.R.id.content)).perform(click());

        Log.d(TAG, "testAsyncTask end");
    }

    @Test
    public void RecycleViewItemCount() {
        hasItemCount(4);
    }

    @Test
    public void isRecyclerViewDisplayed() {
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));
        onView(withId(R.id.recycler_view)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void clickRecyclerViewItem() {
//        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnHolderItem(withRecipeName("Brownies"), click()));
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
    }

    public static Matcher<RecyclerView.ViewHolder> withRecipeName(final String recipeName) {
        return new BoundedMatcher<RecyclerView.ViewHolder, BakeryRecyclerAdapter.ViewHolder>(BakeryRecyclerAdapter.ViewHolder.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("View Holder with Recipe Name: " + recipeName);
            }

            @Override
            protected boolean matchesSafely(BakeryRecyclerAdapter.ViewHolder item) {
                return item.recipeNameTextView.getText().toString().equalsIgnoreCase(recipeName);
            }
        };
    }

    public static ViewAction clickRecyclerViewItem(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click on a child view with a specified id";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View recipeView = view.findViewById(id);
                recipeView.performClick();
            }
        };
    }

    public static ViewAssertion hasItemCount(final int count) {
        return new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                if (!(view instanceof RecyclerView)) {
                    throw noViewFoundException;
                }

                RecyclerView rv = (RecyclerView) view;
                Truth.assertThat(rv.getAdapter().getItemCount()).isEqualTo(count);
            }
        };
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }
}
