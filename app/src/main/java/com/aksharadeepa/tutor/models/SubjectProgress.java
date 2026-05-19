package com.aksharadeepa.tutor.models;

public class SubjectProgress {
    public int subjectId;
    public String subjectName;
    public int totalChapters;
    public int completedChapters;

    public int percent() {
        return totalChapters == 0 ? 0 : Math.round((completedChapters * 100f) / totalChapters);
    }
}
