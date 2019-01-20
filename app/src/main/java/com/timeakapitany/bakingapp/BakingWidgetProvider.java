package com.timeakapitany.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.timeakapitany.bakingapp.detail.DetailActivity;
import com.timeakapitany.bakingapp.model.Recipe;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidgetProvider extends AppWidgetProvider {

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, Recipe recipe, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget_provider);
        if (recipe != null) {
            Intent intent = DetailActivity.newIntent(context, recipe);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.widget, pendingIntent);
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < recipe.getIngredientsList().size(); i++) {
                stringBuilder.append(String.format("- %s: %s %s\n", recipe.getIngredientsList().get(i).getIngredientName(),
                        recipe.getIngredientsList().get(i).getQuantity().toString(),
                        recipe.getIngredientsList().get(i).getMeasure()));
            }

            views.setTextViewText(R.id.appwidget_recipe, recipe.getName());
            views.setTextViewText(R.id.appwidget_ingredient, stringBuilder.toString());
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, null, appWidgetId);
        }
    }

}

