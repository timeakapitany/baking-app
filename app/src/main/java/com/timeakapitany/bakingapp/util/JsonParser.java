package com.timeakapitany.bakingapp.util;

import com.timeakapitany.bakingapp.model.Ingredient;
import com.timeakapitany.bakingapp.model.Recipe;
import com.timeakapitany.bakingapp.model.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonParser {

    public static List<Recipe> parseRecipeJson(String json) {
        List<Recipe> recipeList = new ArrayList<>();

        try {

            JSONArray results = new JSONArray(json);

            Recipe recipe;
            List<Ingredient> ingredientsList;
            List<Step> stepsList;

            for (int i = 0; i < results.length(); i++) {
                recipe = new Recipe();
                JSONObject jsonObject = results.getJSONObject(i);
                if (jsonObject.has("id")) {
                    recipe.setId(jsonObject.getInt("id"));
                }
                if (jsonObject.has("name")) {
                    recipe.setName(jsonObject.getString("name"));
                }
                if (jsonObject.has("ingredients")) {
                    JSONArray ingredients = jsonObject.getJSONArray("ingredients");
                    ingredientsList = new ArrayList<>();

                    Ingredient ingredient;
                    for (int j = 0; j < ingredients.length(); j++) {
                        ingredient = new Ingredient();
                        JSONObject recipeObject = ingredients.getJSONObject(j);
                        if (recipeObject.has("quantity")) {
                            ingredient.setQuantity(recipeObject.getDouble("quantity"));
                        }
                        if (recipeObject.has("measure")) {
                            ingredient.setMeasure(recipeObject.getString("measure"));
                        }
                        if (recipeObject.has("ingredient")) {
                            ingredient.setIngredientName(recipeObject.getString("ingredient"));
                        }

                        ingredientsList.add(ingredient);
                    }
                    recipe.setIngredientsList(ingredientsList);

                }
                if (jsonObject.has("steps")) {
                    JSONArray steps = jsonObject.getJSONArray("steps");
                    stepsList = new ArrayList<>();

                    Step step;
                    for (int j = 0; j < steps.length(); j++) {
                        step = new Step();
                        JSONObject stepObject = steps.getJSONObject(j);
                        if (stepObject.has("id")) {
                            step.setStepId(stepObject.getInt("id"));
                        }
                        if (stepObject.has("shortDescription")) {
                            step.setShortDescription(stepObject.getString("shortDescription"));
                        }
                        if (stepObject.has("description")) {
                            step.setDescription(stepObject.getString("description"));
                        }
                        if (stepObject.has("videoURL")) {
                            step.setVideoUrl(stepObject.getString("videoURL"));
                        }
                        if (stepObject.has("thumbnailURL")) {
                            step.setThumbnailUrl(stepObject.getString("thumbnailURL"));
                        }

                        stepsList.add(step);
                    }
                    recipe.setStepsList(stepsList);

                }
                if (jsonObject.has("servings")) {
                    recipe.setServings(jsonObject.getInt("servings"));
                }
                if (jsonObject.has("image")) {
                    recipe.setImage(jsonObject.getString("image"));
                }


                recipeList.add(recipe);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipeList;
    }
}

