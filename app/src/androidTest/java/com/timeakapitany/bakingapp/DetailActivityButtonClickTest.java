package com.timeakapitany.bakingapp;

import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.DisplayMetrics;

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

        if (!isScreenSw600dp()) {
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

        if (!isScreenSw600dp()) {
            onView(withId(R.id.instruction_recyclerview)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

            onView(withId(R.id.next_button)).perform(click());
            onView(withId(R.id.step_description)).check(matches(withText(testRecipe.getStepsList().get(2).getDescription())));
        }

    }

    private boolean isScreenSw600dp() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activityTestRule.getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float widthDp = displayMetrics.widthPixels / displayMetrics.density;
        float heightDp = displayMetrics.heightPixels / displayMetrics.density;
        float screenSw = Math.min(widthDp, heightDp);
        return screenSw >= 600;
    }

    private Recipe setUpRecipe() {

        Step step1 = new Step(0, "Test Step1", "Test Description1");
        Step step2 = new Step(0, "Test Step2", "Test Description2");
        Step step3 = new Step(0, "Test Step3", "Test Description3");
        List<Step> steps = Arrays.asList(step1, step2, step3);
        Recipe testRecipe = new Recipe(0, "Test Cake", steps);

        return testRecipe;
    }
}
