package com.readex.tissuesampleapp.models;

/**
 * Custom collection item model
 */
public class Collection {

    private String diseaseTerm, title;

    public Collection () {}

    /**
     * Constructor to assign the values
     * @param diseaseTerm
     * @param title
     */
    public Collection(String diseaseTerm, String title) {
        this.diseaseTerm = diseaseTerm;
        this.title = title;
    }

    /**
     * Getters and setters
     */
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
