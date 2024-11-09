package com.janbert.urlCompressor.model;

import jakarta.persistence.*;


@Entity
@Table(name = "url_tracker")
public class URLEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hash_map", length = 16, nullable = false)
    private String hashmap;

    @Column(name = "url_string")
    private String urlString;

    public URLEntity() {
    }

    public URLEntity(String hashmap, String urlString) {
        this.hashmap = hashmap;
        this.urlString = urlString;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHashmap() {
        return hashmap;
    }

    public void setHashmap(String hashmap) {
        this.hashmap = hashmap;
    }

    public String getUrlString() {
        return urlString;
    }

    public void setUrlString(String urlString) {
        this.urlString = urlString;
    }

    @Override
    public String toString() {
        return "URLEntity{" +
                "id=" + id +
                ", hashmap='" + hashmap + '\'' +
                ", urlString='" + urlString + '\'' +
                '}';
    }
}
