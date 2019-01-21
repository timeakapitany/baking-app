package com.timeakapitany.bakingapp.recipes;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.timeakapitany.bakingapp.api.BakingService;
import com.timeakapitany.bakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RecipeViewModel extends AndroidViewModel {
    private static final String TAG = RecipeViewModel.class.getSimpleName();
    public final MutableLiveData<List<Recipe>> recipesLiveData = new MutableLiveData<>();

    public RecipeViewModel(@NonNull Application application) {
        super(application);
        startNetworkCall();
    }

    private void startNetworkCall() {
        createService().listRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                recipesLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: Data load failed");
            }
        });
    }

    private BakingService createService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(BakingService.class);
    }
}
