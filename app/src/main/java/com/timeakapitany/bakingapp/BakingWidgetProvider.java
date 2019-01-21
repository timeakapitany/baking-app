package com.timeakapitany.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.google.gson.Gson;
import com.timeakapitany.bakingapp.detail.DetailActivity;
import com.timeakapitany.bakingapp.model.Recipe;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidgetProvider extends AppWidgetProvider {

    public static final String RECIPE = "recipe";

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, Recipe recipe, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget_provider);
        if (recipe != null) {
            Intent intent = DetailActivity.newIntent(context, recipe);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.widget, pendingIntent);


            views.setTextViewText(R.id.appwidget_recipe, recipe.getName());
            Intent serviceIntent = new Intent(context, BakingWidgetRemoteViewsService.class);
            Bundle bundle = new Bundle();
            bundle.putString(RECIPE, new Gson().toJson(recipe));
            serviceIntent.putExtras(bundle);
            views.setRemoteAdapter(R.id.widget_listview, serviceIntent);

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

