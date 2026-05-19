package com.aksharadeepa.tutor.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.aksharadeepa.tutor.database.AppDatabase
import com.aksharadeepa.tutor.models.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StudyRepository(context: Context) {
    private val db = AppDatabase.get(context)
    private val scope = CoroutineScope(Dispatchers.IO)

    fun subjects(): LiveData<List<Subject>> = db.subjectDao().getAll()

    fun chapters(): LiveData<List<ChapterWithProgress>> = db.chapterDao().chaptersWithProgressLive()

    fun searchChapters(query: String): LiveData<List<ChapterWithProgress>> = db.chapterDao().searchChapters(query)

    fun progress(): LiveData<List<SubjectProgress>> = db.subjectDao().progressLive()

    fun mastery(): LiveData<List<SubjectMastery>> = db.subjectDao().masteryLive()

    fun completedToday(start: Long, end: Long): LiveData<Int> = db.progressDao().completedBetweenLive(start, end)

    fun setChapterCompleted(chapterId: Int, completed: Boolean) {
        scope.launch {
            db.progressDao().upsert(Progress(chapterId, completed, if (completed) System.currentTimeMillis() else 0L))
        }
    }

    fun saveQuizResult(result: QuizResult) {
        scope.launch {
            db.quizResultDao().insert(result)
        }
    }

    suspend fun getQuestions(chapterId: Int): List<QuizQuestion> = withContext(Dispatchers.IO) {
        db.questionDao().getForChapter(chapterId)
    }

    suspend fun getChapter(chapterId: Int): Chapter? = withContext(Dispatchers.IO) {
        db.chapterDao().getById(chapterId)
    }

    suspend fun loginOrCreate(username: String, passwordHash: String): Boolean = withContext(Dispatchers.IO) {
        val user = db.userDao().findByUsername(username)
        if (user == null) {
            db.userDao().insert(User(username = username, passwordHash = passwordHash, createdAt = System.currentTimeMillis()))
            true
        } else {
            passwordHash == user.passwordHash
        }
    }
}
