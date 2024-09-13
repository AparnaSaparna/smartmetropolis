package com.StackJet.SmartMetropolis.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "shops")
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String openingTime;
    private String closingTime;
    private String entertainment;
    private String foodCourt;
    private String anchorStores;
    private String specialFeatures;
    private String parking;
    private String accessibility;
    private String imagePath;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getOpeningTime() {
		return openingTime;
	}
	public void setOpeningTime(String openingTime) {
		this.openingTime = openingTime;
	}
	public String getClosingTime() {
		return closingTime;
	}
	public void setClosingTime(String closingTime) {
		this.closingTime = closingTime;
	}
	public String getEntertainment() {
		return entertainment;
	}
	public void setEntertainment(String entertainment) {
		this.entertainment = entertainment;
	}
	public String getFoodCourt() {
		return foodCourt;
	}
	public void setFoodCourt(String foodCourt) {
		this.foodCourt = foodCourt;
	}
	public String getAnchorStores() {
		return anchorStores;
	}
	public void setAnchorStores(String anchorStores) {
		this.anchorStores = anchorStores;
	}
	public String getSpecialFeatures() {
		return specialFeatures;
	}
	public void setSpecialFeatures(String specialFeatures) {
		this.specialFeatures = specialFeatures;
	}
	public String getParking() {
		return parking;
	}
	public void setParking(String parking) {
		this.parking = parking;
	}
	public String getAccessibility() {
		return accessibility;
	}
	public void setAccessibility(String accessibility) {
		this.accessibility = accessibility;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}


}
