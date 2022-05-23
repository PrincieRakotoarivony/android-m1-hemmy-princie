package com.quiz.services;

import com.google.gson.reflect.TypeToken;
import com.quiz.KidzyApplication;
import com.quiz.models.Categorie;
import com.quiz.models.Comment;
import com.quiz.models.Publication;
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

    public void changeNotif(String id, boolean notif) throws Exception {
        String token = KidzyApplication.get("token");
        MyMap body = new MyMap().putData("notif", notif);
        MyResponse myResponse = Util.executeRequest(Const.BASE_URL + "/theme/" + id + "/notif", Util.POST, body, token);
        if(myResponse.getMeta().getStatus() != 1){
            throw myResponse.getMeta().convertToException();
        }
    }

    public String savePublication(Publication pub) throws Exception {
        String token = KidzyApplication.get("token");
        MyResponse myResponse = Util.executeRequest(Const.BASE_URL + "/publication/save", Util.POST, pub, token);
        if(myResponse.getMeta().getStatus() != 1){
            throw myResponse.getMeta().convertToException();
        }
        String id = myResponse.getData().getAsString();
        return id;
    }

    public List<Publication> findPublications(String search, String id_theme, int pageNumber, int nPerPage) throws Exception {
        String token = KidzyApplication.get("token");
        MyMap body = new MyMap()
                .putData("search", search)
                .putData("id_theme", id_theme)
                .putData("pageNumber", pageNumber)
                .putData("nPerPage", nPerPage);

        MyResponse myResponse = Util.executeRequest(Const.BASE_URL + "/publication/", Util.POST, body, token);
        if(myResponse.getMeta().getStatus() != 1){
            throw myResponse.getMeta().convertToException();
        }

        List<Publication> result = Util.getGson().fromJson(myResponse.getData(), new TypeToken<List<Publication>>(){}.getType());
        return result;
    }

    public Publication findPubById(String id) throws Exception {
        String token = KidzyApplication.get("token");

        MyResponse myResponse = Util.executeRequest(Const.BASE_URL + "/publication/"+id, Util.GET, null, token);
        if(myResponse.getMeta().getStatus() != 1){
            throw myResponse.getMeta().convertToException();
        }

        Publication result = Util.getGson().fromJson(myResponse.getData(), Publication.class);
        return result;
    }

    public String comment(Comment comment) throws Exception {
        String token = KidzyApplication.get("token");

        MyResponse myResponse = Util.executeRequest(Const.BASE_URL + "/publication/comment", Util.POST, comment, token);
        if(myResponse.getMeta().getStatus() != 1){
            throw myResponse.getMeta().convertToException();
        }

        String id = myResponse.getData().getAsString();
        return id;
    }

}
