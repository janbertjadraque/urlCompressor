package com.janbert.urlCompressor.model;

public record UrlDto (Long id, String hashmap, String urlString) {
    public UrlDto(){
        this(null, " ", null);
    }
}
