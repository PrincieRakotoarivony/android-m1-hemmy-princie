package com.quiz.services;

import com.google.gson.JsonObject;
import com.quiz.models.Utilisateur;
import com.quiz.util.Const;
import com.quiz.util.MultipleException;
import com.quiz.util.MyMap;
import com.quiz.util.MyResponse;
import com.quiz.util.Util;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AuthService {
    static AuthService authService;

    private AuthService(){}

    public static AuthService getInstance(){
        if(authService == null) authService = new AuthService();
        return authService;
    }

    public MyMap login(Utilisateur user) throws Exception {
        MyResponse myResponse = Util.executeRequest(Const.BASE_URL + "/auth/login", Util.POST, user, null);
        if(myResponse.getMeta().getStatus() != 1){
            throw myResponse.getMeta().convertToException();
        }

        JsonObject data = myResponse.getData().getAsJsonObject();
        Utilisateur utilisateur = Util.getGson().fromJson(data.get("user"), Utilisateur.class);
        String token = data.get("token").getAsString();
        MyMap result = new MyMap()
                .putData("user", utilisateur)
                .putData("token", token);

        return result;
    }

    public MyMap signUp(Utilisateur user) throws Exception{
        MyResponse myResponse = Util.executeRequest(Const.BASE_URL + "/auth/signUp", Util.POST, user, null);
        if(myResponse.getMeta().getStatus() != 1){
            throw myResponse.getMeta().convertToException();
        }

        JsonObject data = myResponse.getData().getAsJsonObject();
        Utilisateur utilisateur = Util.getGson().fromJson(data.get("user"), Utilisateur.class);
        String token = data.get("token").getAsString();
        MyMap result = new MyMap()
                .putData("user", utilisateur)
                .putData("token", token);
        return result;
    }

    public void logout(String token) throws Exception{
        MyResponse myResponse = Util.executeRequest(Const.BASE_URL + "/auth/logout", Util.DELETE, null, token);
        if(myResponse.getMeta().getStatus() != 1){
            throw myResponse.getMeta().convertToException();
        }
    }
}
