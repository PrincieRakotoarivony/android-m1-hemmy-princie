package com.quiz.models;

import java.util.List;

public class Question {
    QuestionItem target;
    List<QuestionItem> suggestions;

    public QuestionItem getTarget() {
        return target;
    }

    public void setTarget(QuestionItem target) {
        this.target = target;
    }

    public List<QuestionItem> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<QuestionItem> suggestions) {
        this.suggestions = suggestions;
    }
}
