package com.aksharadeepa.tutor.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aksharadeepa.tutor.databinding.ItemSubjectBinding;
import com.aksharadeepa.tutor.models.ChapterWithProgress;
import com.aksharadeepa.tutor.models.Subject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {
    private final ChapterAdapter.Listener listener;
    private final List<Subject> subjects = new ArrayList<>();
    private final List<ChapterWithProgress> chapters = new ArrayList<>();
    private final Set<Integer> expanded = new HashSet<>();

    public SubjectAdapter(ChapterAdapter.Listener listener) {
        this.listener = listener;
    }

    public void submit(List<Subject> newSubjects, List<ChapterWithProgress> newChapters) {
        subjects.clear();
        subjects.addAll(newSubjects);
        chapters.clear();
        chapters.addAll(newChapters);
        for (Subject subject : subjects) {
            expanded.add(subject.id);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSubjectBinding binding = ItemSubjectBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SubjectViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        Subject subject = subjects.get(position);
        List<ChapterWithProgress> own = new ArrayList<>();
        int completed = 0;
        for (ChapterWithProgress chapter : chapters) {
            if (chapter.subjectId == subject.id) {
                own.add(chapter);
                if (chapter.completed) completed++;
            }
        }
        int percent = own.isEmpty() ? 0 : Math.round(completed * 100f / own.size());
        holder.binding.textSubject.setText(subject.name);
        holder.binding.textPercent.setText(percent + "%");
        holder.binding.progressSubject.setProgress(percent, true);
        holder.binding.recyclerChapters.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        ChapterAdapter adapter = new ChapterAdapter(listener);
        holder.binding.recyclerChapters.setAdapter(adapter);
        adapter.submit(own);
        holder.binding.recyclerChapters.setVisibility(expanded.contains(subject.id) ? View.VISIBLE : View.GONE);
        holder.binding.header.setOnClickListener(v -> {
            if (expanded.contains(subject.id)) expanded.remove(subject.id); else expanded.add(subject.id);
            int adapterPosition = holder.getBindingAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                notifyItemChanged(adapterPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    static class SubjectViewHolder extends RecyclerView.ViewHolder {
        final ItemSubjectBinding binding;
        SubjectViewHolder(ItemSubjectBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
