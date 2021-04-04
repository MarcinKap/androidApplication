package com.example.projekt_android.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.projekt_android.model.SavingsIdeasCategories;
import com.example.projekt_android.model.WorkAreas;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@Entity(tableName = "savings_idea")
public class SavingsIdeaEntity {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "external_id")
    private Long externalId;

    @ColumnInfo(name = "idea_subject")
    private String ideaSubject;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "benefits")
    private String benefits;
    @ColumnInfo(name = "profitability")
    private String profitability;
    @ColumnInfo(name = "unit")
    private String unit;
    @ColumnInfo(name = "nature_of_the_benefit")
    private String natureOfTheBenefit;
    @ColumnInfo(name = "date_of_creation")
    private String dateOfCreation;
    @ColumnInfo(name = "average_rating")
    private Float averageRating;
    @ColumnInfo(name = "savings_ideas_categories")
    private SavingsIdeasCategories savingsIdeasCategories;
    @ColumnInfo(name = "work_areas")
    private WorkAreas workAreas;

//    @ColumnInfo(name = "typeOfCosts")
//    private List<Object> typeOfCosts = null;


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
