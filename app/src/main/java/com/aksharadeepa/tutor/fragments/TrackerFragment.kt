package com.aksharadeepa.tutor.fragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.aksharadeepa.tutor.activities.ChapterDetailActivity
import com.aksharadeepa.tutor.activities.QuizActivity
import com.aksharadeepa.tutor.adapters.ChapterAdapter
import com.aksharadeepa.tutor.adapters.SubjectAdapter
import com.aksharadeepa.tutor.databinding.FragmentTrackerBinding
import com.aksharadeepa.tutor.models.ChapterWithProgress
import com.aksharadeepa.tutor.models.Subject
import com.aksharadeepa.tutor.repositories.StudyRepository

class TrackerFragment : Fragment(), ChapterAdapter.Listener {
    private var _binding: FragmentTrackerBinding? = null
    private val binding get() = _binding!!
    private lateinit var repository: StudyRepository
    private lateinit var adapter: SubjectAdapter
    private var subjects: List<Subject> = emptyList()
    private var chapterSource: LiveData<List<ChapterWithProgress>>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTrackerBinding.inflate(inflater, container, false)
        repository = StudyRepository(requireContext())
        adapter = SubjectAdapter(this)
        
        binding.recyclerSubjects.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerSubjects.adapter = adapter
        
        repository.subjects().observe(viewLifecycleOwner) { data ->
            subjects = data
            observeChapters(binding.inputSearch.text.toString())
        }
        
        binding.inputSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                observeChapters(s?.toString() ?: "")
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        
        observeChapters("")
        return binding.root
    }

    private fun observeChapters(query: String) {
        chapterSource?.removeObservers(viewLifecycleOwner)
        val trimmed = query.trim()
        chapterSource = if (trimmed.isEmpty()) repository.chapters() else repository.searchChapters(trimmed)
        chapterSource?.observe(viewLifecycleOwner) { chapters ->
            adapter.submit(subjects, chapters)
        }
    }

    override fun onOpen(chapter: ChapterWithProgress) {
        val intent = Intent(requireContext(), ChapterDetailActivity::class.java).apply {
            putExtra(ChapterDetailActivity.EXTRA_CHAPTER_ID, chapter.id)
            putExtra(ChapterDetailActivity.EXTRA_CHAPTER_TITLE, chapter.title)
            putExtra(ChapterDetailActivity.EXTRA_SUBJECT_ID, chapter.subjectId)
            putExtra(ChapterDetailActivity.EXTRA_COMPLETED, chapter.completed)
        }
        startActivity(intent)
    }

    override fun onCompletedChanged(chapter: ChapterWithProgress, completed: Boolean) {
        repository.setChapterCompleted(chapter.id, completed)
    }

    override fun onQuiz(chapter: ChapterWithProgress) {
        val intent = Intent(requireContext(), QuizActivity::class.java).apply {
            putExtra(QuizActivity.EXTRA_CHAPTER_ID, chapter.id)
            putExtra(QuizActivity.EXTRA_CHAPTER_TITLE, chapter.title)
            putExtra(QuizActivity.EXTRA_SUBJECT_ID, chapter.subjectId)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
