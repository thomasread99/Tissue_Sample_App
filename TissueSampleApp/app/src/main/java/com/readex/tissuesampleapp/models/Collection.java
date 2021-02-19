package com.readex.tissuesampleapp.models;

public class Collection {

    private String diseaseTerm, title;

    public Collection () {}

    public Collection(String diseaseTerm, String title) {
        this.diseaseTerm = diseaseTerm;
        this.title = title;
    }

    public String getDiseaseTerm() {
        return diseaseTerm;
    }

    public void setDiseaseTerm(String diseaseTerm) {
        this.diseaseTerm = diseaseTerm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
