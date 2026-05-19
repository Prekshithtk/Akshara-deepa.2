package com.aksharadeepa.tutor.activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.aksharadeepa.tutor.databinding.ActivityQuizBinding;
import com.aksharadeepa.tutor.databinding.ActivityScoreBinding;
import com.aksharadeepa.tutor.models.QuizQuestion;
import com.aksharadeepa.tutor.models.QuizResult;
import com.aksharadeepa.tutor.repositories.StudyRepository;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    public static final String EXTRA_CHAPTER_ID = "chapter_id";
    public static final String EXTRA_CHAPTER_TITLE = "chapter_title";
    public static final String EXTRA_SUBJECT_ID = "subject_id";
    private static final long QUESTION_MS = 30_000L;

    private ActivityQuizBinding binding;
    private StudyRepository repository;
    private final List<QuizQuestion> questions = new ArrayList<>();
    private int[] answers = new int[0];
    private int index = 0;
    private int chapterId;
    private int subjectId;
    private String chapterTitle;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = new StudyRepository(this);
        chapterId = getIntent().getIntExtra(EXTRA_CHAPTER_ID, -1);
        subjectId = getIntent().getIntExtra(EXTRA_SUBJECT_ID, -1);
        chapterTitle = getIntent().getStringExtra(EXTRA_CHAPTER_TITLE);
        if (savedInstanceState != null) {
            index = savedInstanceState.getInt("index", 0);
            answers = savedInstanceState.getIntArray("answers");
        }
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.groupAnswers.setOnCheckedChangeListener((group, checkedId) -> saveCurrentAnswer());
        binding.buttonPrevious.setOnClickListener(v -> {
            saveCurrentAnswer();
            if (index > 0) {
                index--;
                render();
            }
        });
        binding.buttonNext.setOnClickListener(v -> {
            saveCurrentAnswer();
            if (index == questions.size() - 1) finishQuiz();
            else {
                index++;
                render();
            }
        });
        repository.getQuestions(chapterId, result -> runOnUiThread(() -> {
            questions.clear();
            questions.addAll(result);
            if (answers == null || answers.length != questions.size()) {
                answers = new int[questions.size()];
                for (int i = 0; i < answers.length; i++) answers[i] = -1;
            }
            render();
        }));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveCurrentAnswer();
        outState.putInt("index", index);
        outState.putIntArray("answers", answers);
    }

    private void render() {
        if (questions.isEmpty()) return;
        QuizQuestion q = questions.get(index);
        binding.textTitle.setText(chapterTitle + "  " + (index + 1) + "/" + questions.size());
        binding.textQuestion.setText(q.question);
        binding.answerA.setText(q.optionA);
        binding.answerB.setText(q.optionB);
        binding.answerC.setText(q.optionC);
        binding.answerD.setText(q.optionD);
        binding.groupAnswers.setOnCheckedChangeListener(null);
        binding.groupAnswers.clearCheck();
        if (answers[index] >= 0) {
            ((RadioButton) binding.groupAnswers.getChildAt(answers[index])).setChecked(true);
        }
        binding.groupAnswers.setOnCheckedChangeListener((group, checkedId) -> saveCurrentAnswer());
        binding.progressQuiz.setProgress(Math.round((index + 1) * 100f / questions.size()), true);
        binding.buttonPrevious.setVisibility(index == 0 ? View.INVISIBLE : View.VISIBLE);
        binding.buttonNext.setText(index == questions.size() - 1 ? "Finish" : "Next");
        startTimer();
    }

    private void saveCurrentAnswer() {
        if (answers.length == 0 || questions.isEmpty()) return;
        int checked = binding.groupAnswers.getCheckedRadioButtonId();
        if (checked == binding.answerA.getId()) answers[index] = 0;
        else if (checked == binding.answerB.getId()) answers[index] = 1;
        else if (checked == binding.answerC.getId()) answers[index] = 2;
        else if (checked == binding.answerD.getId()) answers[index] = 3;
    }

    private void startTimer() {
        if (timer != null) timer.cancel();
        timer = new CountDownTimer(QUESTION_MS, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                binding.textTimer.setText("Time left: " + (millisUntilFinished / 1000) + " seconds");
            }

            @Override
            public void onFinish() {
                saveCurrentAnswer();
                if (index < questions.size() - 1) {
                    index++;
                    render();
                } else finishQuiz();
            }
        }.start();
    }

    private void finishQuiz() {
        if (timer != null) timer.cancel();
        int score = 0;
        StringBuilder review = new StringBuilder("Review Answers\n\n");
        for (int i = 0; i < questions.size(); i++) {
            QuizQuestion q = questions.get(i);
            boolean correct = answers[i] == q.correctIndex;
            if (correct) score++;
            review.append(i + 1).append(". ").append(q.question).append("\n")
                    .append(correct ? "Correct" : "Your answer needs revision")
                    .append(" - Correct option: ").append(optionText(q, q.correctIndex)).append("\n")
                    .append(q.explanation).append("\n\n");
        }
        repository.saveQuizResult(new QuizResult(chapterId, subjectId, score, questions.size(), System.currentTimeMillis()));
        ActivityScoreBinding scoreBinding = ActivityScoreBinding.inflate(getLayoutInflater());
        setContentView(scoreBinding.getRoot());
        scoreBinding.textScore.setText("Score: " + score + "/" + questions.size());
        scoreBinding.textReview.setText(review.toString().trim());
        scoreBinding.buttonDone.setOnClickListener(v -> finish());
    }

    private String optionText(QuizQuestion q, int option) {
        if (option == 0) return q.optionA;
        if (option == 1) return q.optionB;
        if (option == 2) return q.optionC;
        return q.optionD;
    }

    @Override
    protected void onDestroy() {
        if (timer != null) timer.cancel();
        super.onDestroy();
    }
}
