package com.timeakapitany.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.timeakapitany.bakingapp.model.Ingredient;
import com.timeakapitany.bakingapp.model.Recipe;

public class BakingWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private final Context context;
    private final Recipe recipe;

    public BakingWidgetRemoteViewsFactory(Context context, Intent intent) {
        this.context = context;
        String recipeString = intent.getStringExtra(BakingWidgetProvider.RECIPE);
        recipe = new Gson().fromJson(recipeString, Recipe.class);

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (recipe == null || recipe.getIngredientsList() == null) {
            return 0;
        } else {
            return recipe.getIngredientsList().size();
        }
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.item_widget_ingredient);
        Ingredient currentIngredient = recipe.getIngredientsList().get(position);

        String ingredientString = String.format("- %s: %s %s", currentIngredient.getIngredientName(),
                currentIngredient.getQuantity().toString(),
                currentIngredient.getMeasure());
        views.setTextViewText(R.id.appwidget_ingredient, ingredientString);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
