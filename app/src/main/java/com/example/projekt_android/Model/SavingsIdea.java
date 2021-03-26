package com.example.projekt_android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SavingsIdea {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("ideaSubject")
    @Expose
    private String ideaSubject;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("benefits")
    @Expose
    private String benefits;
    @SerializedName("profitability")
    @Expose
    private String profitability;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("natureOfTheBenefit")
    @Expose
    private String natureOfTheBenefit;
    @SerializedName("dateOfCreation")
    @Expose
    private String dateOfCreation;
    @SerializedName("averageRating")
    @Expose
    private Float averageRating;
    @SerializedName("savingsIdeasCategories")
    @Expose
    private SavingsIdeasCategories savingsIdeasCategories;
    @SerializedName("workAreas")
    @Expose
    private WorkAreas workAreas;
    @SerializedName("typeOfCosts")
    @Expose
    private List<Object> typeOfCosts = null;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdeaSubject() {
        return ideaSubject;
    }

    public void setIdeaSubject(String ideaSubject) {
        this.ideaSubject = ideaSubject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public String getProfitability() {
        return profitability;
    }

    public void setProfitability(String profitability) {
        this.profitability = profitability;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getNatureOfTheBenefit() {
        return natureOfTheBenefit;
    }

    public void setNatureOfTheBenefit(String natureOfTheBenefit) {
        this.natureOfTheBenefit = natureOfTheBenefit;
    }

    public String getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(String dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public Float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Float averageRating) {
        this.averageRating = averageRating;
    }

    public SavingsIdeasCategories getSavingsIdeasCategories() {
        return savingsIdeasCategories;
    }

    public void setSavingsIdeasCategories(SavingsIdeasCategories savingsIdeasCategories) {
        this.savingsIdeasCategories = savingsIdeasCategories;
    }

    public WorkAreas getWorkAreas() {
        return workAreas;
    }

    public void setWorkAreas(WorkAreas workAreas) {
        this.workAreas = workAreas;
    }

    public List<Object> getTypeOfCosts() {
        return typeOfCosts;
    }

    public void setTypeOfCosts(List<Object> typeOfCosts) {
        this.typeOfCosts = typeOfCosts;
    }




}
