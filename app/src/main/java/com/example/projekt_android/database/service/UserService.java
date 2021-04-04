package com.example.projekt_android.database.service;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.fragment.app.FragmentActivity;

import com.example.projekt_android.database.database.AndroidDatabase;
import com.example.projekt_android.database.entity.UserEntity;
import com.example.projekt_android.mapper.UserMapper;
import com.example.projekt_android.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {

    AndroidDatabase androidDatabase;
    Context context;
    FragmentActivity fragmentActivity;


    public UserService(AndroidDatabase androidDatabase, Context context) {
        this.androidDatabase = androidDatabase;
        this.context = context;
    }
    public UserService(AndroidDatabase androidDatabase, Context context, FragmentActivity fragmentActivity) {
        this.androidDatabase = androidDatabase;
        this.context = context;
        this.fragmentActivity = fragmentActivity;
    }



    public List<Integer> isUserTableEmpty(){
        List<Integer> integers = new ArrayList<>();
        androidDatabase.getQueryExecutor().execute(() -> {
//            final Integer numberOfUsers = androidDatabase.usersEntityDAO().getNumberOfUsers();

            final Integer integer = androidDatabase.usersEntityDAO().getAll().size();
           integers.add(integer);
            System.out.println("ilosc uzytkownikow na liscie" + integers.get(0));

        });

       return integers;
    }


    public List<User> getUserList() {
        List<User> newsList = new ArrayList<>();

        androidDatabase.getQueryExecutor().execute(() -> {
            final Optional<List<UserEntity>> userEntityList = Optional.ofNullable(androidDatabase.usersEntityDAO().getAll());


            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    // code goes here
                    if (userEntityList !=null){
                        for (UserEntity userEntity : userEntityList.get()) {
                            User user = UserMapper.mapFromEntity(userEntity);
                            if (user != null) {
                                newsList.add(user);
                            }
                        }
                    }
                }
            });


        });

        return newsList;
    }


    public void saveUser(UserEntity userEntity){
        androidDatabase.getQueryExecutor().execute(() -> {
            androidDatabase.usersEntityDAO().insert(userEntity);

            System.out.println("zawartosc database user:" +   androidDatabase.usersEntityDAO().getAll().size());

        });

    }




}
