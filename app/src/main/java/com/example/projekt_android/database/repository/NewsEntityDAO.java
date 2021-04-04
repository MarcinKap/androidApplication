package com.example.projekt_android.database.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projekt_android.database.entity.NewsEntity;

import java.util.List;

@Dao
public interface NewsEntityDAO {

    @Query("SELECT * FROM news")
    List<NewsEntity> getAll();

    @Query("SELECT * FROM news WHERE external_id LIKE :externalId")
    NewsEntity getNewsByExternalId(Long externalId);

    @Insert
    void insert(NewsEntity... newsEntity);

    @Update
    public void update(NewsEntity... newsEntity);

    @Delete
    void delete(NewsEntity newsEntity);

    @Query("SELECT EXISTS (SELECT 1 FROM news WHERE external_id = :id)")
    boolean newsExist(Long id);



}
