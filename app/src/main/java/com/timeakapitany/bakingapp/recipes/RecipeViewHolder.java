package com.timeakapitany.bakingapp.recipes;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.timeakapitany.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

class RecipeViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.recipe_title)
    TextView textView;

    RecipeViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
