package course.android.letgo_300432317_303062210;

import android.graphics.Bitmap;

/**
 * Created by Noam on 11/27/2017.
 */

public class Product {

    private int id;
    private String title;
    private String description;
    private int price;
    private String category;
    private String location;
    private Bitmap image1 = null;
    private int UserId = -1;

    public Product() {

    }

//constructor with title and des
public Product(String title,String description) {
    this.title = title;
    this.description=description;


}

    //constructor without description and price
    public Product(String title, Bitmap image1,String loaction,String category) {
        this.title = title;
        this.location=loaction;
        this.category=category;
        this.image1 = image1;

    }
    //constructor without description
    public Product(String title, Bitmap image1,String location,String category,int price) {
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
//full costructor
    public Product(String title, String description, Bitmap image1,String location,String category,int price) {
        this.title = title;
        this.description = description;
        this.location=location;
        this.price=price;
        this.category=category;
        this.image1 = image1;
    }

    //getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Bitmap getImage1() {
        return image1;
    }

    public void setImage1(Bitmap image1) {
        this.image1 = image1;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }







    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }




    public void setUserId(int folderId) {
        this.UserId  = folderId;

    }

    public int getUserId() {
        return UserId;
    }
}
