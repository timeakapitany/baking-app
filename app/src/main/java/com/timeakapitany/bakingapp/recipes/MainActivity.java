package com.timeakapitany.bakingapp.recipes;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.timeakapitany.bakingapp.R;
import com.timeakapitany.bakingapp.model.Recipe;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecipeAdapter recipeAdapter;
    private RecipeViewModel model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupRecyclerView();


        model = ViewModelProviders.of(this).get(RecipeViewModel.class);
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
        recyclerView.setAdapter(recipeAdapter);
    }


}
