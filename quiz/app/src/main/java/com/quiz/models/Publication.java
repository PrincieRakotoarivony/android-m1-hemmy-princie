package com.quiz.models;

import java.sql.Timestamp;

public class Publication {
    String _id;
    String titre;
    String description;
    Timestamp datePub;
    String img;
    String id_theme;
    String id_user;

    Abonnement abonm;
    Utilisateur user;
    Theme theme;

    public Abonnement getAbonm() {
        return abonm;
    }

    public void setAbonm(Abonnement abonm) {
        this.abonm = abonm;
    }

    public Utilisateur getUser() {
        return user;
    }

    public void setUser(Utilisateur user) {
        this.user = user;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDatePub() {
        return datePub;
    }

    public void setDatePub(Timestamp datePub) {
        this.datePub = datePub;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getId_theme() {
        return id_theme;
    }

    public void setId_theme(String id_theme) {
        this.id_theme = id_theme;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }
}
