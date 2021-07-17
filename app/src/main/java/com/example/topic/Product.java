package com.example.topic;

import org.w3c.dom.Text;

public class Product {
    public String address,name,image,housetype,price,phone,id;


    public Product (String id, String address, String name, String image,String housetype,String price,String phone){

        this.id = id;
        this.address = address;
        this.image = image;
        this.name = name;
        this.housetype = housetype;
        this.price = price;
        this.phone = phone;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) { this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getHouseType(){
        return this.housetype;
    }

    public String getPrice(){
        return  this.price;
    }

    public String getPhone(){
        return this.phone;
    }

    public void setName(String name) {
        this.name = name;
    }


}
