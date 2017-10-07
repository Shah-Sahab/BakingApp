package com.bakingapp;

import android.content.Intent;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.bakingapp.src.RecipeStepDetailsActivity;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import org.hamcrest.Matcher;
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
 * Created by Psych on 10/4/17.
 */
@RunWith(AndroidJUnit4.class)
public class RecipeStepzDetailsInstrumentedTest {

    @Rule
    public ActivityTestRule<RecipeStepDetailsActivity> activityTestRule = new ActivityTestRule<>(RecipeStepDetailsActivity.class, false, false);

    @Before
    public void init() {
        activityTestRule.launchActivity(new Intent());
        activityTestRule.getActivity().getSupportFragmentManager().beginTransaction();
    }

    @Test
    public void checkMediaPlayer() {
//        onView(withId(R.id.playerView)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void clickMediaPlayerPlayButton() {
//        onView(withId(R.id.playerView)).perform(getExoPlayerViewAction());
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
                simpleExoPlayerView.performClick();
            }
        };
    }


}
