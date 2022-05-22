package com.quiz.models;

public class PartieSave {
    int id;
    String idUser;
    String idCategorie;
    String partieJson;
    int nbrSuccess;
    int nbrTotal;
    int lastIndex;

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
}
