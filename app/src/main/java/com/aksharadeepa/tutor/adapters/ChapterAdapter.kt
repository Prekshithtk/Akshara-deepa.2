package com.aksharadeepa.tutor.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aksharadeepa.tutor.databinding.ItemChapterBinding;
import com.aksharadeepa.tutor.models.ChapterWithProgress;

import java.util.ArrayList;
import java.util.List;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder> {
    private final Listener listener;
    private final List<ChapterWithProgress> chapters = new ArrayList<>();

    public ChapterAdapter(Listener listener) {
        this.listener = listener;
    }

    public void submit(List<ChapterWithProgress> newChapters) {
        chapters.clear();
        chapters.addAll(newChapters);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemChapterBinding binding = ItemChapterBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ChapterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterViewHolder holder, int position) {
        ChapterWithProgress chapter = chapters.get(position);
        holder.binding.textChapter.setText(chapter.position + ". " + chapter.title);
        holder.binding.checkCompleted.setOnCheckedChangeListener(null);
        holder.binding.checkCompleted.setChecked(chapter.completed);
        holder.binding.checkCompleted.setOnCheckedChangeListener((buttonView, isChecked) -> listener.onCompletedChanged(chapter, isChecked));
        holder.binding.buttonOpen.setOnClickListener(v -> listener.onOpen(chapter));
        holder.binding.buttonComplete.setText(chapter.completed ? "Completed" : "Mark Complete");
        holder.binding.buttonComplete.setEnabled(!chapter.completed);
        holder.binding.buttonComplete.setOnClickListener(v -> listener.onCompletedChanged(chapter, true));
        holder.binding.buttonQuiz.setOnClickListener(v -> listener.onQuiz(chapter));
    }

    @Override
    public int getItemCount() {
        return chapters.size();
    }

    static class ChapterViewHolder extends RecyclerView.ViewHolder {
        final ItemChapterBinding binding;
        ChapterViewHolder(ItemChapterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface Listener {
        void onOpen(ChapterWithProgress chapter);
        void onCompletedChanged(ChapterWithProgress chapter, boolean completed);
        void onQuiz(ChapterWithProgress chapter);
    }
}
