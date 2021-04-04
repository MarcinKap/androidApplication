package com.example.projekt_android.mapper;

import com.example.projekt_android.model.News;
import com.example.projekt_android.database.entity.NewsEntity;

public class NewsMapper {

    public static NewsEntity mapToEntity(News from) {
        if(from == null){
            return null;
        }
        NewsEntity newsEntity = new NewsEntity();

        newsEntity.setExternalId(from.getId());
        newsEntity.setTitle(from.getTitle());
        newsEntity.setCreatedDate(from.getCreatedDate());
        newsEntity.setShortText(from.getShortText());
        newsEntity.setText(from.getText());
        newsEntity.setMovieLink(from.getMovieLink());

        return newsEntity;
    }

    public static News mapFromEntity(NewsEntity to) {
        if(to == null){
            return null;
        }
        News news = new News();

        news.setId(to.getExternalId());
        news.setTitle(to.getTitle());
        news.setCreatedDate(to.getCreatedDate());
        news.setShortText(to.getShortText());
        news.setText(to.getText());
        news.setMovieLink(to.getMovieLink());

        return news;


    }
}
