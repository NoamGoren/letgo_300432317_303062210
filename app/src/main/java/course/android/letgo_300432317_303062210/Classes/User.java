package course.android.letgo_300432317_303062210.Classes;

/**
 * Created by Noam on 11/27/2017.
 */

public class User {

    private int id;
    private String userId;
    private String name;
    private String city;
    private String email;
    private String password;

//    private Product[] userProducts;
//    private Product[] favProducts;

    public User() {

    }
    //constructor without password
    public User(String name,String city,String email) {


        this.name = name;
        this.city = city;
        this.email = email;


    }
    //full constructor
    public User(String name,String city,String email,String password) {


        this.name = name;
        this.city = city;
        this.email = email;
        this.password = password;

    }

    public User(String name) {
        this.name=name;

    }

    //getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
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



    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}

