package course.android.letgo_300432317_303062210.Classes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
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
    private Bitmap image1 = null;
    private String userId;

    public Product() {

    }

    //constructor with id
    public Product(String id) {
        this.id = id;
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
    public Product(String title, Bitmap image1,String loaction,String category) {
        this.title = title;
        this.location=loaction;
        this.category=category;
        this.image1 = image1;

    }
    //constructor without description
    public Product(String title, Bitmap image1,String location,String category,String price) {
        this.title = title;
        this.location=location;
        this.category=category;
        this.image1 = image1;
        this.price=price;
    }
    //constructor without price
    public Product(String title, String description, Bitmap image1,String location,String category) {
        this.title = title;
        this.description = description;
        this.location=location;
        this.category=category;
        this.image1 = image1;
    }

    // costructor without image,userId, id
    public Product(String title, String description,String location,String category,String price) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.price = price;
        this.category=category;
    }



    //full costructor
    public Product(String id,String title, String description, Bitmap image1,String location,String category,String price,String userId) {
        this.id=id;
        this.title = title;
        this.description = description;
        this.location=location;
        this.price=price;
        this.category=category;
        this.image1 = image1;
        this.userId=userId;
    }

    // costructor without image
    public Product(String id, String title, String description, String price, String location, String category, String userId) {
        this.id=id;
        this.title = title;
        this.description = description;
        this.price=price;
        this.location=location;
        this.category=category;

        this.userId=userId;
    }

    // costructor without image and user_id
    public Product(String id, String title, String description, String location, String category, String price) {
        this.id=id;
        this.title = title;
        this.description = description;
        this.location=location;
        this.price=price;
        this.category=category;
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

    public Bitmap getImage1() {
        return image1;
    }
    public void setImage1(Bitmap image1) {
        this.image1 = image1;
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


    public static byte[] getImgAsByteArray(Bitmap bm){
        byte[] res= new byte[0];
        if(bm != null){
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG,0,outputStream);
            res = outputStream.toByteArray();
        }
        return res;
    }

    public void setImg(byte[] imgeArray){
        if(imgeArray!=null){
            this.image1 = BitmapFactory.decodeByteArray(imgeArray, 0, imgeArray.length);
        }
    }

    public static List<Product> parseJson(JSONObject json) {

        List<Product> products = null;
        try {

            products = new ArrayList<Product>();

            JSONArray productsJsonArr = json.getJSONArray("products");

            for (int i = 0; i < productsJsonArr.length(); i++) {
                try {
                    JSONObject iObj = productsJsonArr.getJSONObject(i);
                    Product product = new Product();
                    product.setId(iObj.getString("id"));
                    product.setTitle(iObj.getString("title"));
                    product.setDescription(iObj.getString("description"));
                    product.setPrice(iObj.getString("price"));
                    product.setLocation(iObj.getString("location"));
                    product.setCategory(iObj.getString("category"));
                    product.setUserId(iObj.getString("userId"));

                    products.add(product);

                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }

        return products;
    }


}



