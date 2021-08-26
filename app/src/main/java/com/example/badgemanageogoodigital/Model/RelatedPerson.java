package com.example.badgemanageogoodigital.Model;

public class RelatedPerson {
    private String relatedPersonId;
    private String relatedPersontitle;

    public RelatedPerson(String id, String relatedPersontitle) {
        this.relatedPersonId = id;
        this.relatedPersontitle = relatedPersontitle;
    }

    public RelatedPerson() {
    }

    public String getId() {
        return relatedPersonId;
    }

    public void setId(String id) {
        this.relatedPersonId = id;
    }

    public String getTitle() {
        return relatedPersontitle;
    }

    public void setTitle(String title) {
        this.relatedPersontitle = title;
    }
}
