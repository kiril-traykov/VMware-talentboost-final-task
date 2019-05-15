package com.talentboost.api.api.Models;

import javax.persistence.*;

@Entity
@Table(name = "Memes")
public class Meme {
    @Column(name="title")
    private String title;
    @Column(name="image")
    private String image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Meme(String title, String image) {
        this.title = title;
        this.image = image;
    }


}
