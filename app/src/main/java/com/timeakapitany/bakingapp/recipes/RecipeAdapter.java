package com.timeakapitany.bakingapp.recipes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.timeakapitany.bakingapp.R;
import com.timeakapitany.bakingapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

    private List<Recipe> items = new ArrayList<>();
    private RecipeClickListener recipeClickListener;

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecipeViewHolder holder, final int position) {
        final Recipe currentItem = items.get(position);
        holder.textView.setText(currentItem.getName());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recipeClickListener != null) {
                    recipeClickListener.onRecipeClick(v, currentItem);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<Recipe> recipes) {
        this.items = recipes;
        notifyDataSetChanged();
    }

    public void setRecipeClickListener(RecipeClickListener recipeClickListener) {
        this.recipeClickListener = recipeClickListener;
    }


    public interface RecipeClickListener {

        void onRecipeClick(View v, Recipe recipe);
    }
}
