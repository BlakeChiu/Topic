package com.example.topic.Sightseeing;

public class RecyclerViewPost {

    public String imageName;
    public String title;
    public String address;
    public String info;
    public String item;

    public RecyclerViewPost(String imageName,String title,String address,String info,String item){
        this.imageName = imageName;
        this.title = title;
        this.address = address;
        this.info = info;
        this.item = item;
    }

    public String getImageName() {
        return imageName;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public String getInfo() {
        return info;
    }

    public String getItem() {
        return item;
    }
}
