package com.aksharadeepa.tutor.activities

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.aksharadeepa.tutor.databinding.ActivityQuizBinding
import com.aksharadeepa.tutor.databinding.ActivityScoreBinding
import com.aksharadeepa.tutor.models.QuizQuestion
import com.aksharadeepa.tutor.models.QuizResult
import com.aksharadeepa.tutor.repositories.StudyRepository
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class QuizActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuizBinding
    private lateinit var repository: StudyRepository
    private val questions = mutableListOf<QuizQuestion>()
    private var answers: IntArray = intArrayOf()
    private var index = 0
    private var chapterId = -1
    private var subjectId = -1
    private var chapterTitle: String? = null
    private var timer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = StudyRepository(this)
        chapterId = intent.getIntExtra(EXTRA_CHAPTER_ID, -1)
        subjectId = intent.getIntExtra(EXTRA_SUBJECT_ID, -1)
        chapterTitle = intent.getStringExtra(EXTRA_CHAPTER_TITLE)
        
        if (savedInstanceState != null) {
            index = savedInstanceState.getInt("index", 0)
            answers = savedInstanceState.getIntArray("answers") ?: intArrayOf()
        }
        
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.groupAnswers.setOnCheckedChangeListener { _, _ -> saveCurrentAnswer() }
        
        binding.buttonPrevious.setOnClickListener {
            saveCurrentAnswer()
            if (index > 0) {
                index--
                render()
            }
        }
        
        binding.buttonNext.setOnClickListener {
            saveCurrentAnswer()
            if (index == questions.size - 1) finishQuiz()
            else {
                index++
                render()
            }
        }
        
        lifecycleScope.launch {
            val result = repository.getQuestions(chapterId)
            questions.clear()
            questions.addAll(result)
            if (answers.size != questions.size) {
                answers = IntArray(questions.size) { -1 }
            }
            render()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveCurrentAnswer()
        outState.putInt("index", index)
        outState.putIntArray("answers", answers)
    }

    private fun render() {
        if (questions.isEmpty()) return
        val q = questions[index]
        binding.textTitle.text = "$chapterTitle  ${index + 1}/${questions.size}"
        binding.textQuestion.text = q.question
        binding.answerA.text = q.optionA
        binding.answerB.text = q.optionB
        binding.answerC.text = q.optionC
        binding.answerD.text = q.optionD
        
        binding.groupAnswers.setOnCheckedChangeListener(null)
        binding.groupAnswers.clearCheck()
        if (answers[index] >= 0) {
            (binding.groupAnswers.getChildAt(answers[index]) as? RadioButton)?.isChecked = true
        }
        binding.groupAnswers.setOnCheckedChangeListener { _, _ -> saveCurrentAnswer() }
        
        binding.progressQuiz.setProgress(((index + 1) * 100f / questions.size).roundToInt(), true)
        binding.buttonPrevious.visibility = if (index == 0) View.INVISIBLE else View.VISIBLE
        binding.buttonNext.text = if (index == questions.size - 1) "Finish" else "Next"
        startTimer()
    }

    private fun saveCurrentAnswer() {
        if (answers.isEmpty() || questions.isEmpty()) return
        val checked = binding.groupAnswers.checkedRadioButtonId
        answers[index] = when (checked) {
            binding.answerA.id -> 0
            binding.answerB.id -> 1
            binding.answerC.id -> 2
            binding.answerD.id -> 3
            else -> -1
        }
    }

    private fun startTimer() {
        timer?.cancel()
        timer = object : CountDownTimer(QUESTION_MS, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.textTimer.text = "Time left: ${millisUntilFinished / 1000} seconds"
            }

            override fun onFinish() {
                saveCurrentAnswer()
                if (index < questions.size - 1) {
                    index++
                    render()
                } else finishQuiz()
            }
        }.start()
    }

    private fun finishQuiz() {
        timer?.cancel()
        var score = 0
        val review = StringBuilder("Review Answers\n\n")
        for (i in questions.indices) {
            val q = questions[i]
            val correct = answers[i] == q.correctIndex
            if (correct) score++
            review.append("${i + 1}. ${q.question}\n")
                .append(if (correct) "Correct" else "Your answer needs revision")
                .append(" - Correct option: ${optionText(q, q.correctIndex)}\n")
                .append("${q.explanation}\n\n")
        }
        
        repository.saveQuizResult(QuizResult(chapterId = chapterId, subjectId = subjectId, score = score, total = questions.size, takenAt = System.currentTimeMillis()))
        
        val scoreBinding = ActivityScoreBinding.inflate(layoutInflater)
        setContentView(scoreBinding.root)
        scoreBinding.textScore.text = "Score: $score/${questions.size}"
        scoreBinding.textReview.text = review.toString().trim()
        scoreBinding.buttonDone.setOnClickListener { finish() }
    }

    private fun optionText(q: QuizQuestion, option: Int): String {
        return when (option) {
            0 -> q.optionA
            1 -> q.optionB
            2 -> q.optionC
            else -> q.optionD
        }
    }

    override fun onDestroy() {
        timer?.cancel()
        super.onDestroy()
    }

    companion object {
        const val EXTRA_CHAPTER_ID = "chapter_id"
        const val EXTRA_CHAPTER_TITLE = "chapter_title"
        const val EXTRA_SUBJECT_ID = "subject_id"
        private const val QUESTION_MS = 30_000L
    }
}
