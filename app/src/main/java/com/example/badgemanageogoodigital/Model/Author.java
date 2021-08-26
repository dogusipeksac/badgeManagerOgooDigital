package com.example.badgemanageogoodigital.Model;

public class Author {
    private String authorId;
    private String authorTitle;

    public Author(String authorId, String authorTitle) {
        this.authorId = authorId;
        this.authorTitle = authorTitle;
    }

    public Author() {
    }

    public String getId() {
        return authorId;
    }

    public void setId(String id) {
        this.authorId = id;
    }

    public String getTitle() {
        return authorTitle;
    }

    public void setTitle(String title) {
        this.authorTitle = title;
    }
}
