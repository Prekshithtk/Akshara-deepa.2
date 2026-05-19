package com.aksharadeepa.tutor.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.aksharadeepa.tutor.databinding.FragmentAnalyticsBinding;
import com.aksharadeepa.tutor.models.SubjectMastery;
import com.aksharadeepa.tutor.repositories.StudyRepository;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class AnalyticsFragment extends Fragment {
    private FragmentAnalyticsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAnalyticsBinding.inflate(inflater, container, false);
        new StudyRepository(requireContext()).mastery().observe(getViewLifecycleOwner(), this::render);
        return binding.getRoot();
    }

    private void render(List<SubjectMastery> mastery) {
        List<RadarEntry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        String weakest = "";
        float weakestScore = 101f;
        float strongestScore = -1f;
        String strongest = "";
        binding.layoutMasteryBars.removeAllViews();
        for (SubjectMastery item : mastery) {
            entries.add(new RadarEntry(item.mastery));
            labels.add(item.subjectName);
            addMasteryBar(item);
            if (item.mastery < weakestScore) {
                weakestScore = item.mastery;
                weakest = item.subjectName;
            }
            if (item.mastery > strongestScore) {
                strongestScore = item.mastery;
                strongest = item.subjectName;
            }
        }
        if (entries.isEmpty()) {
            binding.textWeakAreas.setText("Attend quizzes to build your strength map.");
            return;
        }
        RadarDataSet set = new RadarDataSet(entries, "Subject Mastery");
        set.setColor(Color.rgb(46, 125, 111));
        set.setFillColor(Color.rgb(46, 125, 111));
        set.setDrawFilled(true);
        set.setFillAlpha(70);
        set.setLineWidth(2f);
        set.setValueTextSize(12f);
        RadarData data = new RadarData(set);
        data.setValueTextColor(Color.rgb(23, 35, 31));
        binding.radarChart.setData(data);
        binding.radarChart.getDescription().setEnabled(false);
        binding.radarChart.getLegend().setEnabled(false);
        binding.radarChart.getYAxis().setAxisMinimum(0f);
        binding.radarChart.getYAxis().setAxisMaximum(100f);
        XAxis xAxis = binding.radarChart.getXAxis();
        xAxis.setTextSize(13f);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int index = Math.round(value) % labels.size();
                return labels.get(index);
            }
        });
        binding.radarChart.getYAxis().setLabelCount(5, false);
        binding.radarChart.invalidate();
        if (strongestScore == 0) {
            binding.textWeakAreas.setText("No quiz scores yet.\nStart with any chapter quiz. Your mastery bars and radar map will update automatically after every quiz.");
        } else {
            String advice = weakestScore < 50
                    ? "Revise this subject first and retake one chapter quiz."
                    : "Keep practicing mixed questions to maintain balance.";
            binding.textWeakAreas.setText("Weak area: " + weakest + " (" + Math.round(weakestScore) + "%)\n"
                    + "Strong area: " + strongest + " (" + Math.round(strongestScore) + "%)\n"
                    + "Next action: " + advice);
        }
    }

    private void addMasteryBar(SubjectMastery item) {
        TextView label = new TextView(requireContext());
        label.setText(item.subjectName + " - " + Math.round(item.mastery) + "%");
        label.setTextSize(15f);
        label.setTextColor(Color.rgb(23, 35, 31));
        label.setPadding(0, 10, 0, 4);
        binding.layoutMasteryBars.addView(label);

        ProgressBar bar = new ProgressBar(requireContext(), null, android.R.attr.progressBarStyleHorizontal);
        bar.setMax(100);
        bar.setProgress(Math.round(item.mastery));
        bar.setProgressTintList(android.content.res.ColorStateList.valueOf(colorFor(item.mastery)));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dp(12)
        );
        binding.layoutMasteryBars.addView(bar, params);
    }

    private int colorFor(float mastery) {
        if (mastery < 50) return Color.rgb(200, 90, 84);
        if (mastery < 75) return Color.rgb(244, 183, 64);
        return Color.rgb(62, 156, 98);
    }

    private int dp(int value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }
}
