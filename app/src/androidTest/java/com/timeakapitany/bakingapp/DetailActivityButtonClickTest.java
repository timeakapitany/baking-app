package com.timeakapitany.bakingapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.test.espresso.contrib.RecyclerViewActions;
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
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class DetailActivityButtonClickTest {

    @Rule
    public ActivityTestRule<DetailActivity> activityTestRule = new ActivityTestRule<>(DetailActivity.class, false, false);

    @Test
    public void testBackButtonClick_OpensPreviousStep() {
        Recipe testRecipe = setUpRecipe();

        Intent intent = new Intent();
        intent.putExtra(DetailActivity.CURRENT_RECIPE, testRecipe);
        activityTestRule.launchActivity(intent);
        activityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (!TestUtils.isScreenSw600dp(activityTestRule)) {
            onView(withId(R.id.instruction_recyclerview)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
            onView(withId(R.id.back_button)).perform(click());
            onView(withId(R.id.step_description)).check(matches(withText(testRecipe.getStepsList().get(0).getDescription())));
        }
    }

    @Test
    public void testNextButtonClick_OpensNextStep() {
        Recipe testRecipe = setUpRecipe();

        Intent intent = new Intent();
        intent.putExtra(DetailActivity.CURRENT_RECIPE, testRecipe);
        activityTestRule.launchActivity(intent);
        activityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (!TestUtils.isScreenSw600dp(activityTestRule)) {
            onView(withId(R.id.instruction_recyclerview)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
            onView(withId(R.id.next_button)).perform(click());
            onView(withId(R.id.step_description)).check(matches(withText(testRecipe.getStepsList().get(2).getDescription())));
        }
    }

    private Recipe setUpRecipe() {
        Step step1 = new Step(0, "Test Step1", "Test Description1");
        Step step2 = new Step(0, "Test Step2", "Test Description2");
        Step step3 = new Step(0, "Test Step3", "Test Description3");
        List<Step> steps = Arrays.asList(step1, step2, step3);
        return new Recipe(0, "Test Cake", steps);
    }
}
