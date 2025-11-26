package com.example.demo.image.dto;

public class ImageResponse {
    private Long id;
    private String url;
    private String filename;
    private String contentType;
    private long size;

    public ImageResponse(Long id, String url, String filename, String contentType, long size) {
        this.id = id;
        this.url = url;
        this.filename = filename;
        this.contentType = contentType;
        this.size = size;
    }
    public Long getId() { return id; }
    public String getUrl() { return url; }
    public String getFilename() { return filename; }
    public String getContentType() { return contentType; }
    public long getSize() { return size; }
}
