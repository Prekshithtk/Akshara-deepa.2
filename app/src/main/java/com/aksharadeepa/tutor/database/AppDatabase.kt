package com.aksharadeepa.tutor.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aksharadeepa.tutor.dao.*
import com.aksharadeepa.tutor.models.*
import com.aksharadeepa.tutor.utils.CourseContent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

@Database(
    entities = [User::class, Subject::class, Chapter::class, QuizQuestion::class, QuizResult::class, Progress::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun subjectDao(): SubjectDao
    abstract fun chapterDao(): ChapterDao
    abstract fun questionDao(): QuestionDao
    abstract fun quizResultDao(): QuizResultDao
    abstract fun progressDao(): ProgressDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        val EXECUTOR = Executors.newFixedThreadPool(4)

        fun get(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase::class.java,
                    "akshara_deepa.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                seed(instance)
                instance
            }
        }

        private fun seed(db: AppDatabase) {
            CoroutineScope(Dispatchers.IO).launch {
                if (db.subjectDao().count() == 0) {
                    db.subjectDao().insertAll(
                        listOf(
                            Subject(1, "Science", "#2E7D6F"),
                            Subject(2, "Mathematics", "#3F6FAE"),
                            Subject(3, "Social Studies", "#A45A3A")
                        )
                    )

                    val chapters = mutableListOf<Chapter>()
                    var id = 1
                    val science = arrayOf("Electricity", "Light and Reflection", "Acids, Bases and Salts", "Life Processes", "Metals and Non-metals", "Our Environment")
                    val math = arrayOf("Real Numbers", "Polynomials", "Pair of Linear Equations", "Triangles", "Trigonometry", "Statistics")
                    val social = arrayOf("The Advent of Europeans", "Freedom Movement", "Indian Constitution", "Agriculture in India", "Natural Resources", "Economics and Development")
                    
                    id = addChapters(chapters, id, 1, science)
                    id = addChapters(chapters, id, 2, math)
                    addChapters(chapters, id, 3, social)
                    
                    db.chapterDao().insertAll(chapters)
                    db.questionDao().insertAll(CourseContent.makeQuestions(chapters))
                }
            }
        }

        private fun addChapters(chapters: MutableList<Chapter>, startId: Int, subjectId: Int, names: Array<String>): Int {
            var id = startId
            for (i in names.indices) {
                chapters.add(Chapter(id++, subjectId, names[i], i + 1))
            }
            return id
        }
    }
}
