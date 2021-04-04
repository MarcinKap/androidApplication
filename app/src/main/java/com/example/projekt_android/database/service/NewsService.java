package com.example.projekt_android.database.service;


import com.example.projekt_android.model.News;
import com.example.projekt_android.database.database.AndroidDatabase;
import com.example.projekt_android.database.entity.NewsEntity;
import com.example.projekt_android.mapper.NewsMapper;

import java.util.ArrayList;
import java.util.List;

public class NewsService {

    AndroidDatabase androidDatabase;

    public NewsService(AndroidDatabase androidDatabase) {
        this.androidDatabase = androidDatabase;
    }

    public void saveNews(List<News> newsList){

        androidDatabase.getQueryExecutor().execute(() -> {

            final List<NewsEntity> newsEntities = androidDatabase.newsEntityDAO().getAll();
            List<News> newsListToSave = new ArrayList<>();

            if (newsEntities == null){
                // zapis wszystkich newsow do bazy - poniewaz baza jest nullem
                for (News news: newsList){
                    NewsEntity newsEntity = NewsMapper.mapToEntity(news);
                    if (newsEntity!=null){
                        androidDatabase.newsEntityDAO().insert(newsEntity);
                    }
                }
                return;
            }
            // jeśli baza newsEntity nie jest pusta i ma jakieś rekardy to wykonaj to co poniżej
            for (News news: newsList) {
                // jeśli nie ma newsa w bazie danych to zapisz newsa do list
                if(!androidDatabase.newsEntityDAO().newsExist(news.getId())){
                    newsListToSave.add(news);
                }
            }
            // stworzenie NewsEntity na bazie listy newsow do zapisu i zapis ich do bazy
            for (News news: newsListToSave){
                NewsEntity newsEntity = NewsMapper.mapToEntity(news);
                if (newsEntity!=null){
                    androidDatabase.newsEntityDAO().insert(newsEntity);
                }
            }
        }  );
    }

    public List<News> getNewsList(){
        List<News> newsList = new ArrayList<>();
        androidDatabase.getQueryExecutor().execute(()-> {
            final List<NewsEntity> newsEntityList = androidDatabase.newsEntityDAO().getAll();

            for (NewsEntity newsEntity : newsEntityList) {
                News news = NewsMapper.mapFromEntity(newsEntity);
                if (news != null) {
                    newsList.add(news);
                }
            }

        });
        return newsList;

    }

}
