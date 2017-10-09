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
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.core.deps.guava.base.Preconditions.checkNotNull;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withTagValue;
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
        activityTestRule.launchActivity(new Intent());
        activityTestRule.getActivity().getSupportFragmentManager().beginTransaction();
    }

    @Test
    public void checkMediaPlayer() {
//        onView(withTagValue(is((Object) "exo_player_tag_1"))).check(matches(isCompletelyDisplayed()));
//        onView(withId(R.id.playerView)).check(matches(isCompletelyDisplayed()));
//        onView(allOf(withId(R.id.playerView), withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE))).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void clickMediaPlayerPlayButton() {
//        onView(withTagValue(withStringMatching("exo_player_tag_1"))).perform(getExoPlayerViewAction());
//        onView(withTagValue(is((Object)"exo_player_tag_1"))).perform(getExoPlayerViewAction());
//        onView(allOf(withId(R.id.playerView), withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE))).perform(getExoPlayerViewAction());
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
                simpleExoPlayerView.performClick();
            }
        };
    }
}
