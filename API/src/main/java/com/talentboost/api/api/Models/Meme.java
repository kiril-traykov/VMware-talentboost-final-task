package com.talentboost.api.api.Models;

import javax.persistence.*;

@Entity
@Table(name = "Memes")
public class Meme {
    @Column(name="title")
    private String title;
    private String url;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="ID")
    private int id;

    public Meme() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String address) {
        this.url = address;
    }

    public Meme(String title, String url) {
        this.title = title;
        this.url = url;
    }


}
