package com.timeakapitany.bakingapp;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.TextView;

import com.timeakapitany.bakingapp.detail.DetailActivity;
import com.timeakapitany.bakingapp.model.Recipe;
import com.timeakapitany.bakingapp.model.Step;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.AllOf.allOf;


@RunWith(AndroidJUnit4.class)
public class DetailActivityBasicTest {

    @Rule
    public ActivityTestRule<DetailActivity> activityTestRule = new ActivityTestRule<>(DetailActivity.class, false, false);


    @Test
    public void testRecipeDetailActionBar_HasRecipeName() {
        Recipe testRecipe = new Recipe(0, "Test Cake", new ArrayList<Step>());

        Intent intent = new Intent();
        intent.putExtra(DetailActivity.CURRENT_RECIPE, testRecipe);
        activityTestRule.launchActivity(intent);

        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.action_bar)))).check(matches(withText(testRecipe.getName())));

    }

}
