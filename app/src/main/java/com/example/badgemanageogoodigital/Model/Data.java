package com.example.badgemanageogoodigital.Model;

public class Data {

    private String id;
    private int praiseRating;
    private int praiseCount;
    private String creted_date;
    private String message;
    private BadgeData badgeData;
    private Author author;
    private RelatedPerson related_person;
    private String imgUrl;

    public Data(String id, int praiseRating, int praiseCount, String creted_date, String message, BadgeData badgeData,
                Author author, RelatedPerson related_person) {
        this.id = id;
        this.praiseRating = praiseRating;
        this.praiseCount = praiseCount;
        this.creted_date = creted_date;
        this.message = message;
        this.badgeData = badgeData;
        this.author = author;
        this.related_person = related_person;
    }

    public Data(BadgeData badgeData) {
        this.badgeData = badgeData;
    }

    public Data() {
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getId() {
        return id;
    }

    public Data(BadgeData badgeData, Author author, RelatedPerson related_person) {
        this.badgeData = badgeData;
        this.author = author;
        this.related_person = related_person;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPraiseRating() {
        return praiseRating;
    }

    public void setPraiseRating(int praiseRating) {
        this.praiseRating = praiseRating;
    }

    public int getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(int praiseCount) {
        this.praiseCount = praiseCount;
    }

    public String getCreted_date() {
        return creted_date;
    }

    public void setCreted_date(String creted_date) {
        this.creted_date = creted_date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BadgeData getBadgeData() {
        return badgeData;
    }

    public void setBadgeData(BadgeData badgeData) {
        this.badgeData = badgeData;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public RelatedPerson getRelated_person() {
        return related_person;
    }

    public void setRelated_person(RelatedPerson related_person) {
        this.related_person = related_person;
    }
}
