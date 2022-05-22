package com.quiz.services;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.quiz.KidzyApplication;
import com.quiz.models.Categorie;
import com.quiz.models.Utilisateur;
import com.quiz.util.Const;
import com.quiz.util.MyMap;
import com.quiz.util.MyResponse;
import com.quiz.util.Util;

import java.util.ArrayList;
import java.util.List;

public class QuizService {

    static QuizService quizService;

    private QuizService(){}

    public static QuizService getInstance(){
        if(quizService == null) quizService = new QuizService();
        return quizService;
    }

    public List<Categorie> findCategories() throws Exception {
        String token = KidzyApplication.get("token");
        MyResponse myResponse = Util.executeRequest(Const.BASE_URL + "/categorie/", Util.GET, null, token);
        if(myResponse.getMeta().getStatus() != 1){
            throw myResponse.getMeta().convertToException();
        }

        List<Categorie> result = Util.getGson().fromJson(myResponse.getData(), new TypeToken<List<Categorie>>(){}.getType());
        return result;
    }
}
