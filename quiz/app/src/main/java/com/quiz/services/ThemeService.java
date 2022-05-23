package com.quiz.services;

import com.google.gson.reflect.TypeToken;
import com.quiz.KidzyApplication;
import com.quiz.models.Categorie;
import com.quiz.models.Theme;
import com.quiz.util.Const;
import com.quiz.util.MyMap;
import com.quiz.util.MyResponse;
import com.quiz.util.Util;

import java.util.List;

public class ThemeService {
    static ThemeService themeService;

    private ThemeService(){}

    public static ThemeService getInstance(){
        if(themeService == null) themeService = new ThemeService();
        return themeService;
    }

    public List<Theme> findThemes(String search, boolean all, int pageNumber, int nPerPage) throws Exception {
        String token = KidzyApplication.get("token");
        MyMap body = new MyMap()
                .putData("search", search)
                .putData("all", all)
                .putData("pageNumber", pageNumber)
                .putData("nPerPage", nPerPage);

        MyResponse myResponse = Util.executeRequest(Const.BASE_URL + "/theme/", Util.POST, body, token);
        if(myResponse.getMeta().getStatus() != 1){
            throw myResponse.getMeta().convertToException();
        }

        List<Theme> result = Util.getGson().fromJson(myResponse.getData(), new TypeToken<List<Theme>>(){}.getType());
        return result;
    }
}
