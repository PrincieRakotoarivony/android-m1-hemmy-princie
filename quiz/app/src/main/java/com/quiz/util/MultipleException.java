package com.quiz.util;

import java.util.HashMap;

public class MultipleException extends Exception {
    HashMap<String, String> errors;

    public MultipleException(String message, HashMap<String, String> errors){
        super(message);
        this.errors = errors;
    }

    public HashMap<String, String> getErrors() {
        return errors;
    }

    public void setErrors(HashMap<String, String> errors) {
        this.errors = errors;
    }

}
