package com.quiz.util;

import java.util.HashMap;

public class MyMap extends HashMap<String, Object> {
    public MyMap putData(String key, Object value){
        super.put(key, value);
        return this;
    }
}
