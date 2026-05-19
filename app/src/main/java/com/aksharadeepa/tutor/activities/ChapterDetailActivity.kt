package com.aksharadeepa.tutor.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.aksharadeepa.tutor.databinding.ActivityChapterDetailBinding;
import com.aksharadeepa.tutor.repositories.StudyRepository;
import com.aksharadeepa.tutor.utils.CourseContent;

public class ChapterDetailActivity extends AppCompatActivity {
    public static final String EXTRA_CHAPTER_ID = "chapter_id";
    public static final String EXTRA_CHAPTER_TITLE = "chapter_title";
    public static final String EXTRA_SUBJECT_ID = "subject_id";
    public static final String EXTRA_COMPLETED = "completed";

    private ActivityChapterDetailBinding binding;
    private StudyRepository repository;
    private int chapterId;
    private int subjectId;
    private String chapterTitle;
    private boolean completed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChapterDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = new StudyRepository(this);
        chapterId = getIntent().getIntExtra(EXTRA_CHAPTER_ID, -1);
        subjectId = getIntent().getIntExtra(EXTRA_SUBJECT_ID, -1);
        chapterTitle = getIntent().getStringExtra(EXTRA_CHAPTER_TITLE);
        completed = getIntent().getBooleanExtra(EXTRA_COMPLETED, false);

        binding.textChapterTitle.setText(chapterTitle);
        binding.textSubject.setText(subjectName(subjectId));
        binding.textNotes.setText(CourseContent.notesFor(chapterTitle));
        binding.textStudyPlan.setText(CourseContent.studyPlanFor(chapterTitle));
        refreshCompletionUi();

        binding.buttonBack.setOnClickListener(v -> finish());
        binding.buttonMarkComplete.setOnClickListener(v -> markComplete());
        binding.buttonStartQuiz.setOnClickListener(v -> openQuiz());
    }

    private void markComplete() {
        repository.setChapterCompleted(chapterId, true);
        completed = true;
        refreshCompletionUi();
        Toast.makeText(this, "Course marked complete.", Toast.LENGTH_SHORT).show();
    }

    private void refreshCompletionUi() {
        binding.textStatus.setText(completed ? "Completed" : "In progress");
        binding.buttonMarkComplete.setText(completed ? "Course Completed" : "Mark Course Complete");
        binding.buttonMarkComplete.setEnabled(!completed);
    }

    private void openQuiz() {
        Intent intent = new Intent(this, QuizActivity.class);
        intent.putExtra(QuizActivity.EXTRA_CHAPTER_ID, chapterId);
        intent.putExtra(QuizActivity.EXTRA_CHAPTER_TITLE, chapterTitle);
        intent.putExtra(QuizActivity.EXTRA_SUBJECT_ID, subjectId);
        startActivity(intent);
    }

    private String subjectName(int id) {
        if (id == 1) return "Science";
        if (id == 2) return "Mathematics";
        return "Social Studies";
    }

}
