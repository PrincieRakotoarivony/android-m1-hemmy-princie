package com.quiz.util;

import java.util.HashMap;

public class Meta {
    int status;
    String message;
    HashMap<String, String> errors;

    public HashMap<String, String> getErrors() {
        return errors;
    }

    public void setErrors(HashMap<String, String> errors) {
        this.errors = errors;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean hasErrors(){
        return getErrors() != null && !getErrors().isEmpty();
    }

    public Exception convertToException(){
        if(hasErrors()) return new MultipleException(getMessage(), getErrors());
        return new Exception(getMessage());
    }
}
