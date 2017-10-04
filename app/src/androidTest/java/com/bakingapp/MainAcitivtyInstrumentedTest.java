package com.bakingapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.bakingapp.src.MainActivity;
import com.bakingapp.src.model.Recipe;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.hasItemInArray;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

/**
 * Created by Psych on 9/20/17.
 */

@RunWith(AndroidJUnit4.class)
public class MainAcitivtyInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

//    @Before
//    public void verifyRecipeList() throws Exception {
//        onData(allOf(is(instanceOf(Map.class)), hasEntry(equalTo("Nutella Pie"), is("item: 1")))).perform(click());
//    }

    @Test
    public void clickOnRecyclerItem() {
//        onView(withId(R.id.recycler_view)).perform(click()).check(matches(isDisplayed()));
//        onData(allOf(is(instanceOf(Recipe.class)), hasEntry(equalTo("Nutella Pie"), is("item: 0")))).perform(click());
//        onData(allOf(is(instanceOf(Recipe.class)), hasItemInArray(""))).perform(click());
        onData(anything()).inAdapterView(withId(R.id.recipe_name_text)).atPosition(0).perform(click());
    }
}
