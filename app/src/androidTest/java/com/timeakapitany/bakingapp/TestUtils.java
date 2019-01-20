package com.timeakapitany.bakingapp;

import android.support.test.rule.ActivityTestRule;
import android.util.DisplayMetrics;

import com.timeakapitany.bakingapp.detail.DetailActivity;

public class TestUtils {

    static boolean isScreenSw600dp(ActivityTestRule<DetailActivity> activityTestRule) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activityTestRule.getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float widthDp = displayMetrics.widthPixels / displayMetrics.density;
        float heightDp = displayMetrics.heightPixels / displayMetrics.density;
        float screenSw = Math.min(widthDp, heightDp);
        return screenSw >= 600;
    }
}
