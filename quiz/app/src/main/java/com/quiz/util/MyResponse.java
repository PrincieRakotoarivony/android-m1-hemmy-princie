package com.quiz.util;

import com.google.gson.JsonElement;

public class MyResponse {
    Meta meta;
    JsonElement data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }
}
