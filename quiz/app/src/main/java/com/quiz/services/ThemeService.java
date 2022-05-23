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

    public Theme findThemeById(String id) throws Exception {
        String token = KidzyApplication.get("token");
        MyResponse myResponse = Util.executeRequest(Const.BASE_URL + "/theme/" + id, Util.GET, null, token);
        if(myResponse.getMeta().getStatus() != 1){
            throw myResponse.getMeta().convertToException();
        }

        Theme result = Util.getGson().fromJson(myResponse.getData(), Theme.class);
        return result;
    }

    public String subscribe(String id) throws Exception {
        String token = KidzyApplication.get("token");
        MyMap body = new MyMap();
        MyResponse myResponse = Util.executeRequest(Const.BASE_URL + "/theme/" + id + "/subscribe", Util.POST, body, token);
        if(myResponse.getMeta().getStatus() != 1){
            throw myResponse.getMeta().convertToException();
        }

        String subscriptionId = myResponse.getData().getAsString();
        return subscriptionId;
    }

    public void unsubscribe(String id) throws Exception {
        String token = KidzyApplication.get("token");
        MyResponse myResponse = Util.executeRequest(Const.BASE_URL + "/theme/" + id + "/unsubscribe", Util.DELETE, null, token);
        if(myResponse.getMeta().getStatus() != 1){
            throw myResponse.getMeta().convertToException();
        }
    }
}
