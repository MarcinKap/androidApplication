package com.example.projekt_android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class SavingsIdea {

    public SavingsIdea(Long externalId, String ideaSubject, String description, String benefits, String profitability, String unit, String natureOfTheBenefit, String dateOfCreation, Float averageRating, SavingsIdeasCategories savingsIdeasCategories, WorkAreas workAreas) {
        this.externalId = externalId;
        this.ideaSubject = ideaSubject;
        this.description = description;
        this.benefits = benefits;
        this.profitability = profitability;
        this.unit = unit;
        this.natureOfTheBenefit = natureOfTheBenefit;
        this.dateOfCreation = dateOfCreation;
        this.averageRating = averageRating;
        this.savingsIdeasCategories = savingsIdeasCategories;
        this.workAreas = workAreas;
    }


    @SerializedName("id")
    @Expose
    private Long externalId;

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


    public Long getExternalId() {
        return externalId;
    }

    public void setExternalId(Long externalId) {
        this.externalId = externalId;
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
}
