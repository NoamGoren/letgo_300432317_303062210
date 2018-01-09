package course.android.letgo_300432317_303062210.Classes;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Noam on 11/27/2017.
 */

public class User {


    private String name;
    private List<Product> favoritesProducts;

    public User() {
      favoritesProducts=new ArrayList<Product>();
    }

    // constructor without name
    //public User(String id) {
       // this.id = id;
    //}

    // constructor without id
    public User(String name) {
        this.name = name;
      favoritesProducts=new ArrayList<Product>();
    }

    public List<Product> getFavoritesProducts(){
      return  favoritesProducts;
    }


    //getters and setters



    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setFavoritesProducts(Product product){
      favoritesProducts.add(product);

    }

    public static List<User> parseJson(JSONObject json) {

        List<User> users = null;
        try {

            users = new ArrayList<User>();

            JSONArray foldersJsonArr = json.getJSONArray("users");

            for (int i = 0; i < foldersJsonArr.length(); i++) {
                try {
                    JSONObject iObj = foldersJsonArr.getJSONObject(i);
                    User user = new User();
                    user.setName(iObj.getString("name"));

                    users.add(user);

                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }

        return users;
    }


}



