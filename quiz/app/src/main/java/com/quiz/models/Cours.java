package com.quiz.models;

public class Cours {
    String _id;
    String description;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    String image;
    String link;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
