package com.example.Noam.myapplication.backend.objects;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created by Noam on 11/27/2017.
 */

public class Product {

    private String id;
    private String title;
    private String description;
    private String price;
    private String location;
    private String category;
    private byte[] image = null;
    private String userId;

    public Product() {

    }

    public  Product(String id){
        this.id=id;
    }

    //constructor with title and des
    public Product(String title, String description) {
        this.title = title;
        this.description=description;
    }

    //constructor with title, desc , location
    public Product(String title, String description, String location) {
        this.title = title;
        this.description=description;
        this.location=location;
    }

    //constructor without description and price
    public Product(String title, byte[] image, String loaction, String category) {
        this.title = title;
        this.location=loaction;
        this.category=category;
        this.image = image;

    }
    //constructor without description
    public Product(String title, byte[] image, String location, String category, String price) {
        this.title = title;
        this.location=location;
        this.category=category;
        this.image = image;
        this.price=price;
    }
    //constructor without price
    public Product(String title, String description, byte[] image, String location, String category) {
        this.title = title;
        this.description = description;
        this.location=location;
        this.category=category;
        this.image = image;
    }

    // costructor without image
    public Product(String title, String description,String location,String category,String price) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.price = price;
        this.category=category;
    }

    //constructor without price&image
    public Product(String title, String description,String location,String category) {
        this.title = title;
        this.description = description;
        this.location=location;
        this.category=category;
    }


    // costructor
    public Product(String id, String title, String description,String price,String location, String category ,String userId) {
        this.id=id;
        this.title = title;
        this.description = description;
        this.price=price;
        this.location=location;
        this.category=category;
        this.userId=userId;
    }

    //full costructor
    public Product(String productId, String productTitle, String description, String price, String location, String category, byte[] img, String user) {
        this.id = productId;
        this.title = productTitle;
        this.description=description;
        this.price=price;
        this.location=location;
        this.category=category;
        this.image = img;
        this.userId=user;
    }

    //getters and setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public byte[] getImage() {
        return image;
    }
    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public String getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserId() {
        return userId;
    }

    public static String toJson(List<Product> products) {

        JSONObject json=new JSONObject();
        if (products==null){
            return null;
        }
        if (products.size()==0){
            return  null;
        }

        JSONArray jsonArray=new JSONArray();

        for(Product product:products){
            JSONObject productJson= new JSONObject();
            productJson.put("id",product.getId());
            productJson.put("title",product.getTitle());
            productJson.put("description",product.getDescription());
            productJson.put("price",product.getPrice());
            productJson.put("location",product.getLocation());
            productJson.put("category",product.getCategory());
            productJson.put("image",product.getImage());
            productJson.put("userId",product.getUserId());

            jsonArray.add(productJson);

        }
        json.put("products",jsonArray);
        return json.toString(2);
    }
}

