package com.timeakapitany.bakingapp.detail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.timeakapitany.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InstructionStepsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.instruction)
    TextView textView;

    public InstructionStepsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
