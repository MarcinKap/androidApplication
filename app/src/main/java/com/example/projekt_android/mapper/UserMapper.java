package com.example.projekt_android.mapper;

import com.example.projekt_android.model.User;
import com.example.projekt_android.database.entity.UserEntity;

public class UserMapper {

    public static UserEntity mapToEntity(User from) {
        if(from == null){
            return null;
        }
        UserEntity userEntity = new UserEntity();

        userEntity.setEmail(from.getEmail());
        userEntity.setName(from.getName());
        userEntity.setLastName(from.getLastName());
        userEntity.setRole(from.getRole());
        userEntity.setToken(from.getToken());
        userEntity.setExternalId(from.getId());

        return userEntity;
    }

    public static User mapFromEntity(UserEntity to) {
        if(to == null){
            return null;
        }
        User user = new User();

        user.setEmail(to.getEmail());
        user.setName(to.getName());
        user.setLastName(to.getLastName());
        user.setRole(to.getRole());
        user.setToken(to.getToken());
        user.setId(to.getExternalId());


        return user;


    }



}
