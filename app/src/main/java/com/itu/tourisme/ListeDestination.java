package com.itu.tourisme;

public class ListeDestination {
    private int imageResId;
    private String title;
    private String description;
    private String details;
    private String prix;
    private String telephone;

    public ListeDestination(int imageResId, String title, String description, String details, String prix, String telephone) {
        this.imageResId = imageResId;
        this.title = title;
        this.description = description;
        this.details = details;
        this.prix = prix;
        this.telephone = telephone;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
