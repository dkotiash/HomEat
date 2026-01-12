package com.example.demo.dto;

public class ReviewDto {
    private String text;
    private int rating;
    private String authorName;

    // Leerer Konstruktor (wichtig für Frameworks)
    public ReviewDto() {
    }

    // Konstruktor mit Daten (für deinen Mapper)
    public ReviewDto(String text, int rating, String authorName) {
        this.text = text;
        this.rating = rating;
        this.authorName = authorName;
    }

    // --- Getter und Setter ---
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
