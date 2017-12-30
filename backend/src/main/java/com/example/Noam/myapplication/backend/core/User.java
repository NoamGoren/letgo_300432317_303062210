package com.example.Noam.myapplication.backend.core;

/**
 * Created by Noam on 11/27/2017.
 */

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

public class User {

    private String id;
    private String name;
    private String email;
    private String password;

//    private Product[] userProducts;
//    private Product[] favProducts;
//private List<Product> sold
    public User() {

    }
    //constructor without password
    public User(String name,String email) {


        this.name = name;
        this.email = email;


    }
    //full constructor
    public User(String id, String name,String email,String password) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;

    }

    public User(String name) {
        this.name=name;

    }

    //getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
    return name;
}

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public static String toJson(List<User> users) {

        JSONObject json=new JSONObject();
        if (users==null){
            return null;
        }
        if (users.size()==0){
            return  null;
        }

        JSONArray jsonArray=new JSONArray();

        for(User user:users){
            JSONObject userJson= new JSONObject();
            userJson.put("id",user.getId());
            userJson.put("name",user.getName());
            userJson.put("email",user.getEmail());
            userJson.put("password",user.getPassword());
            jsonArray.add(userJson);

        }
        json.put("users",jsonArray);
        return json.toString(2);
    }
}
