package com.aksharadeepa.tutor.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aksharadeepa.tutor.databinding.ItemSubjectSummaryBinding;
import com.aksharadeepa.tutor.models.SubjectProgress;

import java.util.ArrayList;
import java.util.List;

public class SubjectSummaryAdapter extends RecyclerView.Adapter<SubjectSummaryAdapter.Holder> {
    private final List<SubjectProgress> items = new ArrayList<>();

    public void submit(List<SubjectProgress> data) {
        items.clear();
        items.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemSubjectSummaryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        SubjectProgress item = items.get(position);
        holder.binding.textName.setText(item.subjectName + " - " + item.completedChapters + "/" + item.totalChapters + " chapters");
        holder.binding.progress.setProgress(item.percent(), true);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        final ItemSubjectSummaryBinding binding;
        Holder(ItemSubjectSummaryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
