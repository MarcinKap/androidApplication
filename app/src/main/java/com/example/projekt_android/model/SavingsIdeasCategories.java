package com.example.projekt_android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SavingsIdeasCategories {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("categoryName")
    @Expose
    private String categoryName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
