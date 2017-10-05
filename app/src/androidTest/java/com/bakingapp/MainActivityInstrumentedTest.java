package com.bakingapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.test.espresso.Espresso;
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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.view.View.FIND_VIEWS_WITH_TEXT;
import static com.googlecode.eyesfree.utils.LogUtils.TAG;
import static junit.framework.Assert.assertEquals;


/**
 * Created by Psych on 9/20/17.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class, false, false);

    @Before
    public void setUp() throws Exception {
        mainActivityActivityTestRule.launchActivity(new Intent());
        try {
            waitForAsyncTask();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
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
//        onView(withId(R.id.recycler_view)).check(doesntHaveViewWithText("Brownies"));
    }

    @Test
    public void zClickRecyclerViewItem() {
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnHolderItem(withRecipeName("Brownies"), click()));
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

    public static ViewAssertion hasHolderItemAtPosition(final int index, final Matcher<RecyclerView.ViewHolder> viewHolderMatcher) {
        return new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                if (!(view instanceof RecyclerView)) {
                    throw noViewFoundException;
                }
                RecyclerView rv = (RecyclerView) view;
                Assert.assertThat(rv.findViewHolderForAdapterPosition(index), viewHolderMatcher);
            }
        };
    }

    public static ViewAssertion hasViewWithTextAtPosition(final int index, final CharSequence text) {
        return new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                if (!(view instanceof RecyclerView)) {
                    throw noViewFoundException;
                }
                RecyclerView rv = (RecyclerView) view;
                ArrayList<View> outViews = new ArrayList<>();
                rv.findViewHolderForAdapterPosition(index).itemView.findViewsWithText(outViews, text, FIND_VIEWS_WITH_TEXT);
                Truth.assert_().withMessage("There's no view at index " + index + " of recyclerview that has text : " + text)
                                .that(outViews).isNotEmpty();
            }
        };
    }

    public static ViewAssertion doesntHaveViewWithText(final String text) {
        return new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException e) {
                if (!(view instanceof RecyclerView)) {
                    throw e;
                }
                RecyclerView rv = (RecyclerView) view;
                ArrayList<View> outviews = new ArrayList<>();
                for (int index = 0; index < rv.getAdapter().getItemCount(); index++) {
                    rv.findViewHolderForAdapterPosition(index).itemView.findViewsWithText(outviews, text,
                                    FIND_VIEWS_WITH_TEXT);
                    if (outviews.size() > 0) break;
                }
                Truth.assertThat(outviews).isEmpty();
            }
        };
    }

}
