package com.timeakapitany.bakingapp.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.timeakapitany.bakingapp.R;
import com.timeakapitany.bakingapp.model.Recipe;
import com.timeakapitany.bakingapp.model.Step;

public class DetailActivity extends AppCompatActivity
        implements InstructionStepsAdapter.StepClickListener, InstructionStepFragment.StepNavigationListener {

    public static final String CURRENT_RECIPE = "current.recipe";
    private static final String CURRENT_POSITION = "current.position";

    private int currentPosition;
    private Recipe recipe;
    private boolean twoPane;


    public static Intent newIntent(Context context, Recipe recipe) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(CURRENT_RECIPE, recipe);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        twoPane = findViewById(R.id.two_pane) != null;

        if (savedInstanceState != null) {
            recipe = savedInstanceState.getParcelable(CURRENT_RECIPE);
            currentPosition = savedInstanceState.getInt(CURRENT_POSITION);
        } else {
            onNewIntent(getIntent());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setActionBarTitle();
    }

    private void setActionBarTitle() {
        this.getSupportActionBar().setTitle(recipe.getName());
    }

    private void showDetailFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentByTag(RecipeDetailsFragment.TAG + recipe.getId()) == null) {

            for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
                fragmentManager.popBackStackImmediate();
            }

            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_container, RecipeDetailsFragment.newInstance(recipe, twoPane), RecipeDetailsFragment.TAG + recipe.getId())
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(CURRENT_RECIPE, recipe);
        outState.putInt(CURRENT_POSITION, currentPosition);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStepClick(View v, int position) {
        currentPosition = position;
        if (position < recipe.getStepsList().size()) {
            Step currentStep = recipe.getStepsList().get(position);

            InstructionStepFragment instructionStepFragment = InstructionStepFragment.newInstance(currentStep);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Fragment previousFragment = fragmentManager.findFragmentById(R.id.recipe_step_container);
            if (previousFragment != null) {
                fragmentManager.popBackStack();
            }
            fragmentTransaction.replace(R.id.recipe_step_container, instructionStepFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onNextClick(View v) {
        if (currentPosition < recipe.getStepsList().size() - 1) {
            currentPosition++;
        } else {
            currentPosition = 0;
        }
        onStepClick(v, currentPosition);
    }

    @Override
    public void onBackClick(View v) {
        if (currentPosition > 0) {
            currentPosition--;
        } else {
            currentPosition = recipe.getStepsList().size() - 1;
        }
        onStepClick(v, currentPosition);
    }

    @Override
    public void onBackPressed() {
        if (twoPane) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        recipe = intent.getParcelableExtra(CURRENT_RECIPE);

        showDetailFragment();
        if (twoPane) {
            onStepClick(null, currentPosition);
        }
    }


}
