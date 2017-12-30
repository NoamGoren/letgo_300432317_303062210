package com.example.Noam.myapplication.backend.operations;

import com.example.Noam.myapplication.backend.core.PostInfo;
import com.example.Noam.myapplication.backend.core.Product;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by u31_7101 on 25/12/2017.
 */

public class ProductsResProvider {

    public List<Product> getAllProductsByUser(String userId, Connection conn) throws SQLException{
        List<Product> result = new ArrayList<Product>();
        if(userId==null){
            return result;
        }
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("select * from products where userId=?");
            ps.setString(1,userId);
            rs= ps.executeQuery();
            while(rs.next()){
                String productId =rs.getString(1);
                String productTitle = rs.getString(2);
                String description =rs.getString(3);
                String price =rs.getString(4);
                String location =rs.getString(5);
                String category =rs.getString(6);
                Blob imgblob = rs.getBlob(7);
                byte[] img= null;
                if(imgblob!=null){
                    img = imgblob.getBytes(1, (int)imgblob.length());
                }
                String user =rs.getString(8);

                Product product = new Product(productId,productTitle,description,price,location,category,img,user);
                result.add(product);
            }
        }catch (SQLException e){
            e.printStackTrace();
            throw e;
        }
        finally {
            if(rs!=null){
                rs.close();
            }

            if(ps!=null){
                ps.close();
            }
        }


        return result;
    }


    public byte[] getImg(String productId, Connection conn) throws SQLException {
        byte[] result= null;
        ResultSet rs= null;
        PreparedStatement ps= null;
        try{
            ps= conn.prepareStatement("select image from products where id=?");
            ps.setString(1,productId);
            rs= ps.executeQuery();
            while (rs.next()){
                Blob imgblob = rs.getBlob(1);

                if(imgblob!=null){
                    result = imgblob.getBytes(1, (int)imgblob.length());
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
            throw e;
        }finally {
            if (rs!=null){
                rs.close();
            }
            if (ps!=null){
                ps.close();
            }
        }
        return result;
    }

    public boolean insertProduct(Product product, Connection conn) {
        //TODO add code here
        return false;
    }
}
