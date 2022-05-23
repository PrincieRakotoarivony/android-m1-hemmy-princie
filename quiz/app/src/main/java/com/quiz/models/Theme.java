package com.quiz.models;

public class Theme {
    String _id;
    String nom;
    String description;
    String img;

    Abonnement abonm;

    public Abonnement getAbonm() {
        return abonm;
    }

    public void setAbonm(Abonnement abonm) {
        this.abonm = abonm;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
