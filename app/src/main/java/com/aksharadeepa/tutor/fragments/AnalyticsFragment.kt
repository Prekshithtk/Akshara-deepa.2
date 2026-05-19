package com.aksharadeepa.tutor.fragments

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.aksharadeepa.tutor.databinding.FragmentAnalyticsBinding
import com.aksharadeepa.tutor.models.SubjectMastery
import com.aksharadeepa.tutor.repositories.StudyRepository
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlin.math.roundToInt

class AnalyticsFragment : Fragment() {
    private var _binding: FragmentAnalyticsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAnalyticsBinding.inflate(inflater, container, false)
        StudyRepository(requireContext()).mastery().observe(viewLifecycleOwner) { render(it) }
        return binding.root
    }

    private fun render(mastery: List<SubjectMastery>) {
        val entries = mutableListOf<RadarEntry>()
        val labels = mutableListOf<String>()
        var weakest = ""
        var weakestScore = 101f
        var strongestScore = -1f
        var strongest = ""

        binding.layoutMasteryBars.removeAllViews()
        for (item in mastery) {
            entries.add(RadarEntry(item.mastery))
            labels.add(item.subjectName)
            addMasteryBar(item)
            if (item.mastery < weakestScore) {
                weakestScore = item.mastery
                weakest = item.subjectName
            }
            if (item.mastery > strongestScore) {
                strongestScore = item.mastery
                strongest = item.subjectName
            }
        }

        if (entries.isEmpty()) {
            binding.textWeakAreas.text = "Attend quizzes to build your strength map."
            return
        }

        val set = RadarDataSet(entries, "Subject Mastery").apply {
            color = Color.rgb(46, 125, 111)
            fillColor = Color.rgb(46, 125, 111)
            setDrawFilled(true)
            fillAlpha = 70
            lineWidth = 2f
            valueTextSize = 12f
        }

        val data = RadarData(set).apply {
            setValueTextColor(Color.rgb(23, 35, 31))
        }

        binding.radarChart.apply {
            this.data = data
            description.isEnabled = false
            legend.isEnabled = false
            yAxis.axisMinimum = 0f
            yAxis.axisMaximum = 100f
            xAxis.textSize = 13f
            xAxis.valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    if (labels.isEmpty()) return ""
                    val index = value.roundToInt() % labels.size
                    return labels[index]
                }
            }
            yAxis.setLabelCount(5, false)
            invalidate()
        }

        if (strongestScore == 0f) {
            binding.textWeakAreas.text = "No quiz scores yet.\nStart with any chapter quiz. Your mastery bars and radar map will update automatically after every quiz."
        } else {
            val advice = if (weakestScore < 50)
                "Revise this subject first and retake one chapter quiz."
            else
                "Keep practicing mixed questions to maintain balance."
            binding.textWeakAreas.text = "Weak area: $weakest (${weakestScore.roundToInt()}%)\n" +
                    "Strong area: $strongest (${strongestScore.roundToInt()}%)\n" +
                    "Next action: $advice"
        }
    }

    private fun addMasteryBar(item: SubjectMastery) {
        val label = TextView(requireContext()).apply {
            text = "${item.subjectName} - ${item.mastery.roundToInt()}%"
            textSize = 15f
            setTextColor(Color.rgb(23, 35, 31))
            setPadding(0, 10, 0, 4)
        }
        binding.layoutMasteryBars.addView(label)

        val bar = ProgressBar(requireContext(), null, android.R.attr.progressBarStyleHorizontal).apply {
            max = 100
            progress = item.mastery.roundToInt()
            progressTintList = ColorStateList.valueOf(colorFor(item.mastery))
        }
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            dp(12)
        )
        binding.layoutMasteryBars.addView(bar, params)
    }

    private fun colorFor(mastery: Float): Int {
        return when {
            mastery < 50 -> Color.rgb(200, 90, 84)
            mastery < 75 -> Color.rgb(244, 183, 64)
            else -> Color.rgb(62, 156, 98)
        }
    }

    private fun dp(value: Int): Int {
        return (value * resources.displayMetrics.density).roundToInt()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
