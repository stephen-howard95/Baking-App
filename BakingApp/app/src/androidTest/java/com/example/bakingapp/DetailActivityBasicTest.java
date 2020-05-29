package com.example.bakingapp;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.anything;

@RunWith(AndroidJUnit4.class)
public class DetailActivityBasicTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testRecipeClickedIsCorrect(){
        onData(anything()).inAdapterView(withId(R.id.recipe_list)).atPosition(1).perform(click());

        onView(withId(R.id.recipe_name_text_view)).check(matches(anyOf(withText("Nutella Pie"),
                withText("Yellow Cake"), withText("Brownies"), withText("Cheesecake"))));
    }
}
