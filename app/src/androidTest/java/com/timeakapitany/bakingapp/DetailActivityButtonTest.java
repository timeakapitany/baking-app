package com.timeakapitany.bakingapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.timeakapitany.bakingapp.detail.DetailActivity;
import com.timeakapitany.bakingapp.model.Recipe;
import com.timeakapitany.bakingapp.model.Step;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class DetailActivityButtonTest {

    @Rule
    public ActivityTestRule<DetailActivity> activityTestRule = new ActivityTestRule<>(DetailActivity.class, false, false);

    @Test
    public void testBackNextButton_IsVisibleOnPortraitPhoneOnly() {
        Step step1 = new Step(0, "Test Step1", "Test Description1");
        List<Step> steps = Arrays.asList(step1);
        Recipe testRecipe = new Recipe(0, "Test Cake", steps);

        Intent intent = new Intent();
        intent.putExtra(DetailActivity.CURRENT_RECIPE, testRecipe);
        activityTestRule.launchActivity(intent);
        activityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        onView(withId(R.id.instruction_recyclerview)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        if (TestUtils.isScreenSw600dp(activityTestRule)) {
            onView(withId(R.id.back_button)).check(doesNotExist());
            onView(withId(R.id.next_button)).check(doesNotExist());
        } else {
            onView(withId(R.id.back_button)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
            onView(withId(R.id.next_button)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        }
    }

    @Test
    public void testBackNextButton_IsNotVisibleOnLandscape() {
        Step step1 = new Step(0, "Test Step1", "Test Description1");
        List<Step> steps = Arrays.asList(step1);
        Recipe testRecipe = new Recipe(0, "Test Cake", steps);

        Intent intent = new Intent();
        intent.putExtra(DetailActivity.CURRENT_RECIPE, testRecipe);
        activityTestRule.launchActivity(intent);
        activityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        onView(withId(R.id.instruction_recyclerview)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.back_button)).check(doesNotExist());
        onView(withId(R.id.next_button)).check(doesNotExist());
    }

}
