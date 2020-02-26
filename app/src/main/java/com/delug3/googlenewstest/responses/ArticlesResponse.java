package com.delug3.googlenewstest.responses;

import com.delug3.googlenewstest.models.Articles;

import java.util.ArrayList;

public class ArticlesResponse {
    private ArrayList<Articles> articles;


    public ArrayList<Articles> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Articles> articles) {
        this.articles = articles;
    }

}
