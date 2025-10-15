package com.example.demo;

public class HomEatEntry {
    public HomEatEntry(String title, String description) {
        this.title = title;
        this.description = description;
    }
    private String title;
    private String description;

    @Override
    public String toString() {
        return "ToDoEntry{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
