package com.timeakapitany.bakingapp.detail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.timeakapitany.bakingapp.R;
import com.timeakapitany.bakingapp.model.Step;

import java.util.ArrayList;
import java.util.List;

class InstructionStepsAdapter extends RecyclerView.Adapter<InstructionStepsViewHolder> {

    private final boolean needsHighlight;
    private List<Step> items = new ArrayList<>();
    private int currentPosition;
    private StepClickListener clickListener;

    InstructionStepsAdapter(boolean needsHighlight, int position) {
        this.needsHighlight = needsHighlight;
        this.currentPosition = position;
    }

    int getCurrentPosition() {
        return currentPosition;
    }

    boolean isNeedsHighlight() {
        return needsHighlight;
    }

    @NonNull
    @Override
    public InstructionStepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_instruction, parent, false);
        return new InstructionStepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final InstructionStepsViewHolder holder, final int position) {
        final Step currentItem = items.get(position);
        holder.textView.setText(currentItem.getShortDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int previousPosition = currentPosition;
                currentPosition = holder.getAdapterPosition();
                if (clickListener != null) {
                    clickListener.onStepClick(v, holder.getAdapterPosition());
                }
                notifyItemChanged(previousPosition);
                notifyItemChanged(currentPosition);
            }
        });

        if (needsHighlight && currentPosition == position) {
            holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.selectedItem));
        } else {
            holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.unSelectedItem));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    void setItems(List<Step> steps) {
        this.items = steps;
        notifyDataSetChanged();
    }

    void setClickListener(StepClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface StepClickListener {

        void onStepClick(View v, int position);
    }

}
