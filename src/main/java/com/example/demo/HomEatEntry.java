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
}
