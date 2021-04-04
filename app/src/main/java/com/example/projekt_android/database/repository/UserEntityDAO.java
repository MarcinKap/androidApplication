package com.example.projekt_android.database.repository;

import androidx.annotation.Nullable;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projekt_android.database.entity.UserEntity;

import java.util.List;

@Dao
public interface UserEntityDAO {

    @Nullable
    @Query("SELECT * FROM user_table")
    List<UserEntity> getAll();

    @Query("SELECT COUNT(*) FROM user_table")
    Integer getNumberOfUsers();

    @Insert
    void insert(UserEntity... userEntities);

    @Update
    public void update(UserEntity... userEntities);

    @Delete
    void delete(UserEntity userEntity);


//    @Insert
//    void insert(UserEntity userEntity);
//
//    @Query("SELECT * FROM user_table")
//    Optional<List<UserEntity>> getAll();
//
    @Query("DELETE FROM user_table")
    void deleteAll();

}
