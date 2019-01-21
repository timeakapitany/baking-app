package com.timeakapitany.bakingapp.recipes;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.timeakapitany.bakingapp.BakingWidgetProvider;
import com.timeakapitany.bakingapp.R;
import com.timeakapitany.bakingapp.detail.DetailActivity;
import com.timeakapitany.bakingapp.model.Recipe;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeClickListener {

    private RecipeAdapter recipeAdapter;
    private boolean fromWidget;
    private RecipeAdapter.RecipeClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (AppWidgetManager.ACTION_APPWIDGET_CONFIGURE.equals(getIntent().getAction())) {
            Toast.makeText(this, "Select a recipe for ingredients' widget", Toast.LENGTH_SHORT).show();
            fromWidget = true;
        }

        listener = this;
        setupRecyclerView();

        RecipeViewModel model = ViewModelProviders.of(this).get(RecipeViewModel.class);
        model.recipesLiveData.observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                recipeAdapter.setItems(recipes);
            }
        });
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.recipe_grid_span_count)));
        recipeAdapter = new RecipeAdapter();
        recipeAdapter.setRecipeClickListener(listener);
        recyclerView.setAdapter(recipeAdapter);
    }

    @Override
    public void onRecipeClick(View v, Recipe recipe) {
        if (fromWidget) {
            configureAppWidget(this, recipe);
        } else {
            Intent intent = DetailActivity.newIntent(v.getContext(), recipe);
            startActivity(intent);
        }
    }

    private void configureAppWidget(Context context, Recipe recipe) {
        int mAppWidgetId;
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            BakingWidgetProvider.updateAppWidget(context, appWidgetManager, recipe, mAppWidgetId);

            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);

            setResult(RESULT_OK, intent);
            finish();
        }
    }

}
