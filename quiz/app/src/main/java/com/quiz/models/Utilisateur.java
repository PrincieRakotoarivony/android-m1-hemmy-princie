package com.quiz.models;

import java.sql.Date;
import java.sql.Timestamp;

public class Utilisateur {
    String _id;
    String mail;
    String mdp;
    String nom;
    String prenom;
    Timestamp dateNaissance;
    String confirmMdp;

    public String getConfirmMdp() {
        return confirmMdp;
    }

    public void setConfirmMdp(String confirmMdp) {
        this.confirmMdp = confirmMdp;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Timestamp getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Timestamp dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
}
