package com.aksharadeepa.tutor.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.aksharadeepa.tutor.adapters.SubjectSummaryAdapter;
import com.aksharadeepa.tutor.databinding.FragmentDashboardBinding;
import com.aksharadeepa.tutor.models.SubjectProgress;
import com.aksharadeepa.tutor.repositories.StudyRepository;
import com.aksharadeepa.tutor.utils.DateUtils;
import com.aksharadeepa.tutor.utils.PreferenceManager;

import java.util.List;

public class DashboardFragment extends Fragment {
    private FragmentDashboardBinding binding;
    private StudyRepository repository;
    private PreferenceManager prefs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        repository = new StudyRepository(requireContext());
        prefs = new PreferenceManager(requireContext());
        binding.textWelcome.setText("Namaste, " + prefs.getUsername());
        String[] quotes = {
                "Small steady study wins big exams.",
                "One chapter today makes tomorrow lighter.",
                "Mistakes in practice become marks in the exam."
        };
        binding.textQuote.setText(quotes[(int) (DateUtils.dayKey() % quotes.length)]);
        SubjectSummaryAdapter adapter = new SubjectSummaryAdapter();
        binding.recyclerSubjectSummary.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerSubjectSummary.setAdapter(adapter);
        repository.progress().observe(getViewLifecycleOwner(), data -> updateProgress(data, adapter));
        long start = DateUtils.startOfToday();
        repository.completedToday(start, start + 86_400_000L).observe(getViewLifecycleOwner(), count -> {
            if (count != null && count > 0) prefs.updateStreakIfNeeded(DateUtils.dayKey());
            binding.textStreak.setText("Today: " + (count == null ? 0 : count) + " topic(s) completed - Streak: " + prefs.getStreak() + " day(s)");
        });
        return binding.getRoot();
    }

    private void updateProgress(List<SubjectProgress> data, SubjectSummaryAdapter adapter) {
        adapter.submit(data);
        int total = 0;
        int completed = 0;
        for (SubjectProgress item : data) {
            total += item.totalChapters;
            completed += item.completedChapters;
        }
        int percent = total == 0 ? 0 : Math.round(completed * 100f / total);
        binding.textOverall.setText("Overall Progress: " + percent + "%");
        binding.progressOverall.setProgress(percent, true);
        String badge = percent >= 100 ? "Badge: Syllabus Finisher" : percent >= 50 ? "Badge: Halfway Scholar" : completed > 0 ? "Badge: First Step Achieved" : "Badge: Complete one topic to unlock your first badge";
        binding.textBadges.setText(badge);
    }
}
