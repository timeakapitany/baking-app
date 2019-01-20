package com.timeakapitany.bakingapp.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.timeakapitany.bakingapp.R;
import com.timeakapitany.bakingapp.model.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsFragment extends Fragment {

    public static final String TAG = "RecipeDetailsFragment";
    private static final String CURRENT_POSITION = "current.position";
    private static final String NEEDS_HIGHLIGHT = "needs.highlight";
    private static final String CURRENT_RECIPE = "current.recipe";

    @BindView(R.id.instruction_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.ingredients)
    CardView ingredientsCardView;

    private InstructionStepsAdapter instructionAdapter;
    private Recipe recipeItem;
    private InstructionStepsAdapter.StepClickListener listener;

    public static RecipeDetailsFragment newInstance(@NonNull Recipe recipe, boolean needsHighlight) {
        Bundle args = new Bundle();
        args.putParcelable(CURRENT_RECIPE, recipe);
        args.putBoolean(RecipeDetailsFragment.NEEDS_HIGHLIGHT, needsHighlight);
        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int position = 0;
        boolean needsHighlight = true;
        if (savedInstanceState != null) {
            position = savedInstanceState.getInt(CURRENT_POSITION);
            needsHighlight = savedInstanceState.getBoolean(NEEDS_HIGHLIGHT);
            recipeItem = savedInstanceState.getParcelable(CURRENT_RECIPE);
        } else if (getArguments() != null) {
            needsHighlight = getArguments().getBoolean(NEEDS_HIGHLIGHT);
            recipeItem = getArguments().getParcelable(CURRENT_RECIPE);
        }
        instructionAdapter = new InstructionStepsAdapter(needsHighlight, position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = ((InstructionStepsAdapter.StepClickListener) context);
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement StepNavigationListener");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe, container, false);
        ButterKnife.bind(this, rootView);

        instructionAdapter.setItems(recipeItem.getStepsList());
        instructionAdapter.setClickListener(listener);
        setupRecyclerView();

        ingredientsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), IngredientDetailActivity.class);
                intent.putExtra(IngredientDetailActivity.CURRENT_RECIPE, recipeItem);
                startActivity(intent);
            }
        });

        return rootView;
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        recyclerView.setAdapter(instructionAdapter);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(CURRENT_POSITION, instructionAdapter.getCurrentPosition());
        outState.putBoolean(NEEDS_HIGHLIGHT, instructionAdapter.isNeedsHighlight());
        outState.putParcelable(CURRENT_RECIPE, recipeItem);
        super.onSaveInstanceState(outState);
    }
}
