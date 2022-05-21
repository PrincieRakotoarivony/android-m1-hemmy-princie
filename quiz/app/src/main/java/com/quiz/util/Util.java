package com.quiz.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.quiz.models.Utilisateur;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Util {
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";

    private static Gson gson;
    private static JsonParser jsonParser;
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    public static Gson getGson(){
        if(gson == null) {
            gson = new GsonBuilder().create();
        }
        return gson;
    }

    public static JsonParser getJsonParser(){
        if(jsonParser == null) {
            jsonParser = new JsonParser();
        }
        return jsonParser;
    }

    public static JsonObject parse(String json){
        return getJsonParser().parse(json).getAsJsonObject();
    }

    public static MyResponse executeRequest(String url, String method, Object body, String auth) throws Exception{
        Response response = null;
        try {
            OkHttpClient client = new OkHttpClient();

            RequestBody requestBody = null;
            if(body != null){
                requestBody = RequestBody.create(Util.getGson().toJson(body), JSON);
            }

            Request.Builder requestBuilder = new Request.Builder()
                    .url(url)
                    .method(method, requestBody);

            if(auth != null)
                requestBuilder.header("Authorization", "Bearer "+auth);

            Request request = requestBuilder.build();

            response = client.newCall(request).execute();
            return Util.getGson().fromJson(response.body().string(), MyResponse.class);

        } finally {
            if(response != null) response.close();
        }
    }

}
