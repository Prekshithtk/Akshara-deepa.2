package com.aksharadeepa.tutor.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.aksharadeepa.tutor.database.AppDatabase;
import com.aksharadeepa.tutor.models.Chapter;
import com.aksharadeepa.tutor.models.ChapterWithProgress;
import com.aksharadeepa.tutor.models.Progress;
import com.aksharadeepa.tutor.models.QuizQuestion;
import com.aksharadeepa.tutor.models.QuizResult;
import com.aksharadeepa.tutor.models.Subject;
import com.aksharadeepa.tutor.models.SubjectMastery;
import com.aksharadeepa.tutor.models.SubjectProgress;
import com.aksharadeepa.tutor.models.User;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class StudyRepository {
    private final AppDatabase db;
    private final ExecutorService executor = AppDatabase.EXECUTOR;

    public StudyRepository(Context context) {
        db = AppDatabase.get(context);
    }

    public LiveData<List<Subject>> subjects() {
        return db.subjectDao().getAll();
    }

    public LiveData<List<ChapterWithProgress>> chapters() {
        return db.chapterDao().chaptersWithProgressLive();
    }

    public LiveData<List<ChapterWithProgress>> searchChapters(String query) {
        return db.chapterDao().searchChapters(query);
    }

    public LiveData<List<SubjectProgress>> progress() {
        return db.subjectDao().progressLive();
    }

    public LiveData<List<SubjectMastery>> mastery() {
        return db.subjectDao().masteryLive();
    }

    public LiveData<Integer> completedToday(long start, long end) {
        return db.progressDao().completedBetweenLive(start, end);
    }

    public void setChapterCompleted(int chapterId, boolean completed) {
        executor.execute(() -> db.progressDao().upsert(new Progress(chapterId, completed, completed ? System.currentTimeMillis() : 0L)));
    }

    public void saveQuizResult(QuizResult result) {
        executor.execute(() -> db.quizResultDao().insert(result));
    }

    public void getQuestions(int chapterId, Callback<List<QuizQuestion>> callback) {
        executor.execute(() -> callback.onResult(db.questionDao().getForChapter(chapterId)));
    }

    public void getChapter(int chapterId, Callback<Chapter> callback) {
        executor.execute(() -> callback.onResult(db.chapterDao().getById(chapterId)));
    }

    public void loginOrCreate(String username, String passwordHash, Callback<Boolean> callback) {
        executor.execute(() -> {
            User user = db.userDao().findByUsername(username);
            if (user == null) {
                db.userDao().insert(new User(username, passwordHash, System.currentTimeMillis()));
                callback.onResult(true);
            } else {
                callback.onResult(passwordHash.equals(user.passwordHash));
            }
        });
    }

    public interface Callback<T> {
        void onResult(T result);
    }
}
