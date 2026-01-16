package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Review {

    @Id
    @GeneratedValue
    private Long id;

    private String text;
    private int rating;
    private String authorName;

    public Review() {
    }

    public Review(String text, int rating, String authorName) {
        this.text = text;
        this.rating = rating;
        this.authorName = authorName;
    }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public Long getId() { return id; }
}