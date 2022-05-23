package com.quiz.models;

public class Abonnement {
    String _id;
    String id_user;
    String id_theme;
    boolean notif;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_theme() {
        return id_theme;
    }

    public void setId_theme(String id_theme) {
        this.id_theme = id_theme;
    }

    public boolean isNotif() {
        return notif;
    }

    public void setNotif(boolean notif) {
        this.notif = notif;
    }
}
