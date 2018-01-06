package com.example.Noam.myapplication.backend.objects;

/**
 * Created by Noam on 11/27/2017.
 */

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

public class User {

    private String name;


    //    private Product[] userProducts;
//    private Product[] favProducts;
//private List<Product> sold
    public User() {

    }
    //constructor without password
    public User(String name) {

        this.name = name;
    }



    //getters and setters


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
            userJson.put("name",user.getName());
            jsonArray.add(userJson);

        }
        json.put("users",jsonArray);
        return json.toString(2);
    }
}
