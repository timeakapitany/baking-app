package com.timeakapitany.bakingapp.recipes;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.timeakapitany.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.recipe_title)
    TextView textView;

    public RecipeViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
