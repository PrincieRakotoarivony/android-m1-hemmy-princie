package com.quiz.models;

import com.google.gson.reflect.TypeToken;
import com.quiz.util.Util;

import java.util.List;

public class PartieSave {
    int id;
    String idUser;
    String idCategorie;
    String partieJson;
    int nbrSuccess;
    int nbrTotal;
    int lastIndex;

    public PartieSave(){

    }

    public PartieSave(String idUser, String idCategorie, List<Question> questions){
        this.idUser = idUser;
        this.idCategorie = idCategorie;
        this.partieJson = Util.getGson().toJson(questions);
        this.nbrSuccess = 0;
        this.nbrTotal = questions.size();
        this.lastIndex = 0;
    }
    public int getLastIndex() {
        return lastIndex;
    }

    public void setLastIndex(int lastIndex) {
        this.lastIndex = lastIndex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(String idCategorie) {
        this.idCategorie = idCategorie;
    }

    public String getPartieJson() {
        return partieJson;
    }

    public void setPartieJson(String partieJson) {
        this.partieJson = partieJson;
    }

    public int getNbrSuccess() {
        return nbrSuccess;
    }

    public void setNbrSuccess(int nbrSuccess) {
        this.nbrSuccess = nbrSuccess;
    }

    public int getNbrTotal() {
        return nbrTotal;
    }

    public void setNbrTotal(int nbrTotal) {
        this.nbrTotal = nbrTotal;
    }

    public List<Question> getQuestions(){
        return Util.getGson().fromJson(getPartieJson(), new TypeToken<List<Question>>(){}.getType());
    }

    public Question getCurrentQuestion(){
        return getQuestions().get(getLastIndex());
    }
}
