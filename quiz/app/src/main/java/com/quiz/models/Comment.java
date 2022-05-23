package com.quiz.models;

import java.sql.Timestamp;

public class Comment {
    String _id;
    Timestamp dateComment;
    String id_user;
    String id_pub;
    String content;
    Utilisateur user;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Timestamp getDateComment() {
        return dateComment;
    }

    public void setDateComment(Timestamp dateComment) {
        this.dateComment = dateComment;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_pub() {
        return id_pub;
    }

    public void setId_pub(String id_pub) {
        this.id_pub = id_pub;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Utilisateur getUser() {
        return user;
    }

    public void setUser(Utilisateur user) {
        this.user = user;
    }
}
