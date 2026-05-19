package com.aksharadeepa.tutor.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aksharadeepa.tutor.databinding.ActivityChapterDetailBinding
import com.aksharadeepa.tutor.repositories.StudyRepository
import com.aksharadeepa.tutor.utils.CourseContent

class ChapterDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChapterDetailBinding
    private lateinit var repository: StudyRepository
    private var chapterId: Int = -1
    private var subjectId: Int = -1
    private var chapterTitle: String? = null
    private var completed: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChapterDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        repository = StudyRepository(this)
        chapterId = intent.getIntExtra(EXTRA_CHAPTER_ID, -1)
        subjectId = intent.getIntExtra(EXTRA_SUBJECT_ID, -1)
        chapterTitle = intent.getStringExtra(EXTRA_CHAPTER_TITLE)
        completed = intent.getBooleanExtra(EXTRA_COMPLETED, false)

        binding.textChapterTitle.text = chapterTitle
        binding.textSubject.text = subjectName(subjectId)
        binding.textNotes.text = CourseContent.notesFor(chapterTitle ?: "")
        binding.textStudyPlan.text = CourseContent.studyPlanFor(chapterTitle ?: "")
        refreshCompletionUi()

        binding.buttonBack.setOnClickListener { finish() }
        binding.buttonMarkComplete.setOnClickListener { markComplete() }
        binding.buttonStartQuiz.setOnClickListener { openQuiz() }
    }

    private fun markComplete() {
        repository.setChapterCompleted(chapterId, true)
        completed = true
        refreshCompletionUi()
        Toast.makeText(this, "Course marked complete.", Toast.LENGTH_SHORT).show()
    }

    private fun refreshCompletionUi() {
        binding.textStatus.text = if (completed) "Completed" else "In progress"
        binding.buttonMarkComplete.text = if (completed) "Course Completed" else "Mark Course Complete"
        binding.buttonMarkComplete.isEnabled = !completed
    }

    private fun openQuiz() {
        val intent = Intent(this, QuizActivity::class.java).apply {
            putExtra(QuizActivity.EXTRA_CHAPTER_ID, chapterId)
            putExtra(QuizActivity.EXTRA_CHAPTER_TITLE, chapterTitle)
            putExtra(QuizActivity.EXTRA_SUBJECT_ID, subjectId)
        }
        startActivity(intent)
    }

    private fun subjectName(id: Int): String {
        return when (id) {
            1 -> "Science"
            2 -> "Mathematics"
            else -> "Social Studies"
        }
    }

    companion object {
        const val EXTRA_CHAPTER_ID = "chapter_id"
        const val EXTRA_CHAPTER_TITLE = "chapter_title"
        const val EXTRA_SUBJECT_ID = "subject_id"
        const val EXTRA_COMPLETED = "completed"
    }
}
