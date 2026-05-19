package com.aksharadeepa.tutor.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.aksharadeepa.tutor.activities.QuizActivity;
import com.aksharadeepa.tutor.activities.ChapterDetailActivity;
import com.aksharadeepa.tutor.adapters.ChapterAdapter;
import com.aksharadeepa.tutor.adapters.SubjectAdapter;
import com.aksharadeepa.tutor.databinding.FragmentTrackerBinding;
import com.aksharadeepa.tutor.models.ChapterWithProgress;
import com.aksharadeepa.tutor.models.Subject;
import com.aksharadeepa.tutor.repositories.StudyRepository;

import java.util.ArrayList;
import java.util.List;

public class TrackerFragment extends Fragment implements ChapterAdapter.Listener {
    private FragmentTrackerBinding binding;
    private StudyRepository repository;
    private SubjectAdapter adapter;
    private List<Subject> subjects = new ArrayList<>();
    private LiveData<List<ChapterWithProgress>> chapterSource;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTrackerBinding.inflate(inflater, container, false);
        repository = new StudyRepository(requireContext());
        adapter = new SubjectAdapter(this);
        binding.recyclerSubjects.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerSubjects.setAdapter(adapter);
        repository.subjects().observe(getViewLifecycleOwner(), data -> {
            subjects = data;
            observeChapters(String.valueOf(binding.inputSearch.getText()));
        });
        binding.inputSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { observeChapters(s.toString()); }
            @Override public void afterTextChanged(Editable s) {}
        });
        observeChapters("");
        return binding.getRoot();
    }

    private void observeChapters(String query) {
        if (chapterSource != null) chapterSource.removeObservers(getViewLifecycleOwner());
        chapterSource = query == null || query.trim().isEmpty() ? repository.chapters() : repository.searchChapters(query.trim());
        chapterSource.observe(getViewLifecycleOwner(), chapters -> adapter.submit(subjects, chapters));
    }

    @Override
    public void onOpen(ChapterWithProgress chapter) {
        Intent intent = new Intent(requireContext(), ChapterDetailActivity.class);
        intent.putExtra(ChapterDetailActivity.EXTRA_CHAPTER_ID, chapter.id);
        intent.putExtra(ChapterDetailActivity.EXTRA_CHAPTER_TITLE, chapter.title);
        intent.putExtra(ChapterDetailActivity.EXTRA_SUBJECT_ID, chapter.subjectId);
        intent.putExtra(ChapterDetailActivity.EXTRA_COMPLETED, chapter.completed);
        startActivity(intent);
    }

    @Override
    public void onCompletedChanged(ChapterWithProgress chapter, boolean completed) {
        repository.setChapterCompleted(chapter.id, completed);
    }

    @Override
    public void onQuiz(ChapterWithProgress chapter) {
        Intent intent = new Intent(requireContext(), QuizActivity.class);
        intent.putExtra(QuizActivity.EXTRA_CHAPTER_ID, chapter.id);
        intent.putExtra(QuizActivity.EXTRA_CHAPTER_TITLE, chapter.title);
        intent.putExtra(QuizActivity.EXTRA_SUBJECT_ID, chapter.subjectId);
        startActivity(intent);
    }
}
