package com.example.projekt_android.database.entity;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity(tableName = "user_table")
public class UserEntity {

    public UserEntity() {
    }


    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "external_id")
    private Long externalId;

    @Nullable
    @ColumnInfo(name = "email")
    private String email;
    @Nullable
    @ColumnInfo(name = "name")
    private String name;
    @Nullable
    @ColumnInfo(name = "last_name")
    private String lastName;
    @Nullable
    @ColumnInfo(name = "role")
    private String role;
    @Nullable
    @ColumnInfo(name = "token")
    private String token;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getExternalId() {
        return externalId;
    }

    public void setExternalId(Long externalId) {
        this.externalId = externalId;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    public void setEmail(@Nullable String email) {
        this.email = email;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @Nullable
    public String getLastName() {
        return lastName;
    }

    public void setLastName(@Nullable String lastName) {
        this.lastName = lastName;
    }

    @Nullable
    public String getRole() {
        return role;
    }

    public void setRole(@Nullable String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
