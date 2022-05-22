package com.quiz.ui.slideshow;

import android.widget.Button;

public class MyChoiceButton {
    Button btn;
    boolean isCorrect;

    public MyChoiceButton(Button btn, boolean isCorrect){
        this.btn = btn;
        this.isCorrect = isCorrect;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
