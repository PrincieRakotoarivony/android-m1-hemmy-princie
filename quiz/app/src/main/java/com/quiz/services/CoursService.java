package com.quiz.services;

import com.google.gson.reflect.TypeToken;
import com.quiz.KidzyApplication;
import com.quiz.models.Categorie;
import com.quiz.models.Cours;
import com.quiz.util.Const;
import com.quiz.util.MyMap;
import com.quiz.util.MyResponse;
import com.quiz.util.Util;

import java.util.List;

public class CoursService {
    static CoursService coursService;

    private CoursService(){}

    public static CoursService getInstance(){
        if(coursService == null) coursService = new CoursService();
        return coursService;
    }

    public List<Cours> findCours(String search, int pageNumber, int nPerPage) throws Exception {
        String token = KidzyApplication.get("token");
        MyMap body = new MyMap()
                .putData("search", search)
                .putData("pageNumber", pageNumber)
                .putData("nPerPage", nPerPage);

        MyResponse myResponse = Util.executeRequest(Const.BASE_URL + "/cours/", Util.POST, body, token);
        if(myResponse.getMeta().getStatus() != 1){
            throw myResponse.getMeta().convertToException();
        }

        List<Cours> result = Util.getGson().fromJson(myResponse.getData(), new TypeToken<List<Cours>>(){}.getType());
        return result;
    }


}
