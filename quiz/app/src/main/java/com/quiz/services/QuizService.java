package com.quiz.services;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.quiz.KidzyApplication;
import com.quiz.models.Categorie;
import com.quiz.models.PartieSave;
import com.quiz.models.Question;
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

    public void deleteLastPartie(String idCategorie){
        SQLiteDatabase db = null;
        try{
            db = KidzyApplication.getDbHelper().getWritableDatabase();
            db.beginTransaction();
            int count = db.delete("partie", " idUser = ? and idCategorie = ? ", new String[]{KidzyApplication.get("idUser"), idCategorie});
            System.out.println("Count = "+count);
            db.setTransactionSuccessful();
        } finally {
            if(db != null){
                if(db.inTransaction())
                    db.endTransaction();
                db.close();
            }
        }
    }

    public void savePartie(PartieSave p){
        SQLiteDatabase db = null;
        try{
            db = KidzyApplication.getDbHelper().getWritableDatabase();
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put("idUser", p.getIdUser());
            values.put("idCategorie", p.getIdCategorie());
            values.put("partieJson", p.getPartieJson());
            values.put("nbrSuccess", p.getNbrSuccess());
            values.put("nbrTotal", p.getNbrTotal());
            values.put("lastIndex", p.getLastIndex());
            long id = db.insert("partie", null, values);
            System.out.println("id = "+id);
            db.setTransactionSuccessful();
        } finally {
            if(db != null){
                if(db.inTransaction())
                    db.endTransaction();
                db.close();
            }
        }
    }

    @SuppressLint("Range")
    public PartieSave getLastPartie(String idCategorie) {
        SQLiteDatabase db = null;
        Cursor c = null;
        try {

            db = KidzyApplication.getDbHelper().getReadableDatabase();
            c = db.rawQuery("select * from partie where idUser = ? and idCategorie = ? ", new String[]{KidzyApplication.get("idUser"), idCategorie});
            if(c.moveToNext()){
                PartieSave p = new PartieSave();
                p.setId(c.getInt(c.getColumnIndex("id")));
                p.setIdCategorie(c.getString(c.getColumnIndex("idCategorie")));
                p.setIdUser(c.getString(c.getColumnIndex("idUser")));
                p.setNbrSuccess(c.getInt(c.getColumnIndex("nbrSuccess")));
                p.setNbrTotal(c.getInt(c.getColumnIndex("nbrTotal")));
                p.setLastIndex(c.getInt(c.getColumnIndex("lastIndex")));
                p.setPartieJson(c.getString(c.getColumnIndex("partieJson")));
                return p;
            }
            return null;
        } finally {
            if(db != null) db.close();
        }
    }



    public List<Question> getQuestions(String idCategorie) throws Exception{
        String token = KidzyApplication.get("token");
        MyResponse myResponse = Util.executeRequest(Const.BASE_URL + "/partie/save", Util.POST, new MyMap().putData("id_categorie", idCategorie), token);
        if(myResponse.getMeta().getStatus() != 1){
            throw myResponse.getMeta().convertToException();
        }

        List<Question> result = Util.getGson().fromJson(myResponse.getData(), new TypeToken<List<Question>>(){}.getType());
        return result;
    }

    public void updatePartie(PartieSave p){
        SQLiteDatabase db = null;
        try{
            db = KidzyApplication.getDbHelper().getWritableDatabase();
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put("nbrSuccess", p.getNbrSuccess());
            values.put("lastIndex", p.getLastIndex());
            int count = db.update("partie", values, " idUser = ? and idCategorie = ? ", new String[]{p.getIdUser(), p.getIdCategorie()});
            System.out.println("count = "+count);
            db.setTransactionSuccessful();
        } finally {
            if(db != null){
                if(db.inTransaction())
                    db.endTransaction();
                db.close();
            }
        }
    }

}
