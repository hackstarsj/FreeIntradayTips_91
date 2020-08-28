package com.silverlinesoftwares.intratips.models;

public class NewsModel {
    String title;
    String links;
    String images;
    String descriptions;
    String dates;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public NewsModel(String title, String links, String images, String descriptions, String dates) {
        this.title = title;
        this.links = links;
        this.images = images;
        this.descriptions = descriptions;
        this.dates = dates;
    }
}
