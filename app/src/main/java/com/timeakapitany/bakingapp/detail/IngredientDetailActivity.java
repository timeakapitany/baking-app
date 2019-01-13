package com.timeakapitany.bakingapp.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.timeakapitany.bakingapp.R;
import com.timeakapitany.bakingapp.model.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientDetailActivity extends AppCompatActivity {

    public static final String CURRENT_RECIPE = "ingredient.list";
    @BindView(R.id.ingredient_detail)
    TextView ingredientDetail;
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingredient_detail);
        ButterKnife.bind(this);

        recipe = getIntent().getParcelableExtra(IngredientDetailActivity.CURRENT_RECIPE);
        this.getSupportActionBar().setSubtitle(recipe.getName());
        ingredientDetail.setText(createIngredientListing(recipe));

    }

    private String createIngredientListing(Recipe recipe) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < recipe.getIngredientsList().size(); i++) {
            stringBuilder.append(String.format("- %s: %s %s\n", recipe.getIngredientsList().get(i).getIngredientName(),
                    recipe.getIngredientsList().get(i).getQuantity().toString(),
                    recipe.getIngredientsList().get(i).getMeasure()));
        }
        return stringBuilder.toString();

    }


}
