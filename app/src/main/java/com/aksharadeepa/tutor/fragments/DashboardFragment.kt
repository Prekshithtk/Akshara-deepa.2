package com.aksharadeepa.tutor.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.aksharadeepa.tutor.adapters.SubjectSummaryAdapter
import com.aksharadeepa.tutor.databinding.FragmentDashboardBinding
import com.aksharadeepa.tutor.models.SubjectProgress
import com.aksharadeepa.tutor.repositories.StudyRepository
import com.aksharadeepa.tutor.utils.DateUtils
import com.aksharadeepa.tutor.utils.PreferenceManager
import kotlin.math.roundToInt

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var repository: StudyRepository
    private lateinit var prefs: PreferenceManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        repository = StudyRepository(requireContext())
        prefs = PreferenceManager(requireContext())
        
        binding.textWelcome.text = "Namaste, ${prefs.username}"
        
        val quotes = arrayOf(
            "Small steady study wins big exams.",
            "One chapter today makes tomorrow lighter.",
            "Mistakes in practice become marks in the exam."
        )
        binding.textQuote.text = quotes[(DateUtils.dayKey() % quotes.size).toInt()]
        
        val adapter = SubjectSummaryAdapter()
        binding.recyclerSubjectSummary.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerSubjectSummary.adapter = adapter
        
        repository.progress().observe(viewLifecycleOwner) { data ->
            updateProgress(data, adapter)
        }
        
        val start = DateUtils.startOfToday()
        repository.completedToday(start, start + 86_400_000L).observe(viewLifecycleOwner) { count ->
            val c = count ?: 0
            if (c > 0) prefs.updateStreakIfNeeded(DateUtils.dayKey())
            binding.textStreak.text = "Today: $c topic(s) completed - Streak: ${prefs.streak} day(s)"
        }
        
        return binding.root
    }

    private fun updateProgress(data: List<SubjectProgress>, adapter: SubjectSummaryAdapter) {
        adapter.submit(data)
        var total = 0
        var completed = 0
        for (item in data) {
            total += item.totalChapters
            completed += item.completedChapters
        }
        val percent = if (total == 0) 0 else (completed * 100f / total).roundToInt()
        binding.textOverall.text = "Overall Progress: $percent%"
        binding.progressOverall.setProgress(percent, true)
        
        val badge = when {
            percent >= 100 -> "Badge: Syllabus Finisher"
            percent >= 50 -> "Badge: Halfway Scholar"
            completed > 0 -> "Badge: First Step Achieved"
            else -> "Badge: Complete one topic to unlock your first badge"
        }
        binding.textBadges.text = badge
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
