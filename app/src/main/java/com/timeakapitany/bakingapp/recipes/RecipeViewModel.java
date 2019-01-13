package com.timeakapitany.bakingapp.recipes;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.timeakapitany.bakingapp.R;
import com.timeakapitany.bakingapp.model.Recipe;
import com.timeakapitany.bakingapp.util.JsonParser;
import com.timeakapitany.bakingapp.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

public class RecipeViewModel extends AndroidViewModel {
    public MutableLiveData<List<Recipe>> recipesLiveData = new MutableLiveData<>();
    private int recipeUrl = R.string.recipe_url;

    public RecipeViewModel(@NonNull Application application) {
        super(application);
        startNetworkCall();
    }


    private void startNetworkCall() {
        RecipesAsyncTask recipesAsyncTask = new RecipesAsyncTask();
        recipesAsyncTask.execute(getApplication().getResources().getString(recipeUrl));
    }

    @SuppressLint("StaticFieldLeak")
    class RecipesAsyncTask extends AsyncTask<String, Void, List<Recipe>> {
        private static final String TAG = "RecipesAsyncTask";


        @Override
        protected List<Recipe> doInBackground(String... strings) {
            Log.d(TAG, "doInBackground: " + strings[0]);
            List<Recipe> recipes = new ArrayList<>();
            String recipeFeed = NetworkUtils.downloadData(strings[0]);
            if (recipeFeed != null) {
                recipes = JsonParser.parseRecipeJson(recipeFeed);
            }
            return recipes;
        }

        @Override
        protected void onPostExecute(List<Recipe> recipes) {
            super.onPostExecute(recipes);
            Log.d(TAG, "onPostExecute: " + recipes);
            recipesLiveData.postValue(recipes);
        }
    }

}
