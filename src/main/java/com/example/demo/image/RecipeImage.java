package com.example.demo.image;

import com.example.demo.recipe.Recipe;
import jakarta.persistence.*;

@Entity
public class RecipeImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;
    private String contentType;
    private long size;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] data;

    @ManyToOne(fetch = FetchType.LAZY)
    private Recipe recipe;

    // --- getters/setters ---
    public Long getId() { return id; }
    public String getFilename() { return filename; }
    public void setFilename(String filename) { this.filename = filename; }
    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }
    public long getSize() { return size; }
    public void setSize(long size) { this.size = size; }
    public byte[] getData() { return data; }
    public void setData(byte[] data) { this.data = data; }
    public Recipe getRecipe() { return recipe; }
    public void setRecipe(Recipe recipe) { this.recipe = recipe; }
}
