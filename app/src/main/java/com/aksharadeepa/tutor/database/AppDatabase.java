package com.aksharadeepa.tutor.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.aksharadeepa.tutor.dao.ChapterDao;
import com.aksharadeepa.tutor.dao.ProgressDao;
import com.aksharadeepa.tutor.dao.QuestionDao;
import com.aksharadeepa.tutor.dao.QuizResultDao;
import com.aksharadeepa.tutor.dao.SubjectDao;
import com.aksharadeepa.tutor.dao.UserDao;
import com.aksharadeepa.tutor.models.Chapter;
import com.aksharadeepa.tutor.models.Progress;
import com.aksharadeepa.tutor.models.QuizQuestion;
import com.aksharadeepa.tutor.models.QuizResult;
import com.aksharadeepa.tutor.models.Subject;
import com.aksharadeepa.tutor.models.User;
import com.aksharadeepa.tutor.utils.CourseContent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Subject.class, Chapter.class, QuizQuestion.class, QuizResult.class, Progress.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;
    public static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(4);

    public abstract UserDao userDao();
    public abstract SubjectDao subjectDao();
    public abstract ChapterDao chapterDao();
    public abstract QuestionDao questionDao();
    public abstract QuizResultDao quizResultDao();
    public abstract ProgressDao progressDao();

    public static AppDatabase get(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "akshara_deepa.db")
                            .fallbackToDestructiveMigration()
                            .build();
                    seed(INSTANCE);
                }
            }
        }
        return INSTANCE;
    }

    private static void seed(AppDatabase db) {
        EXECUTOR.execute(() -> {
            db.subjectDao().insertAll(Arrays.asList(
                    new Subject(1, "Science", "#2E7D6F"),
                    new Subject(2, "Mathematics", "#3F6FAE"),
                    new Subject(3, "Social Studies", "#A45A3A")
            ));

            List<Chapter> chapters = new ArrayList<>();
            int id = 1;
            String[] science = {"Electricity", "Light and Reflection", "Acids, Bases and Salts", "Life Processes", "Metals and Non-metals", "Our Environment"};
            String[] math = {"Real Numbers", "Polynomials", "Pair of Linear Equations", "Triangles", "Trigonometry", "Statistics"};
            String[] social = {"The Advent of Europeans", "Freedom Movement", "Indian Constitution", "Agriculture in India", "Natural Resources", "Economics and Development"};
            id = addChapters(chapters, id, 1, science);
            id = addChapters(chapters, id, 2, math);
            addChapters(chapters, id, 3, social);
            db.chapterDao().insertAll(chapters);
            db.questionDao().insertAll(CourseContent.makeQuestions(chapters));
        });
    }

    private static int addChapters(List<Chapter> chapters, int id, int subjectId, String[] names) {
        for (int i = 0; i < names.length; i++) {
            chapters.add(new Chapter(id++, subjectId, names[i], i + 1));
        }
        return id;
    }

}
