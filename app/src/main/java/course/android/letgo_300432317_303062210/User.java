package course.android.letgo_300432317_303062210;

/**
 * Created by Noam on 11/27/2017.
 */

public class User {

    private int id;
    private String name;
    private String city;
    private String email;
    private String password;

//    private Product[] userProducts;
//    private Product[] favProducts;

//    public InfoFolder() {
//
//    }
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
}

