package com.example.Noam.myapplication.backend.operations;

import com.example.Noam.myapplication.backend.objects.Product;
import com.example.Noam.myapplication.backend.objects.User;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;


public class ProductsResProvider {

    private static final String update_sql = "UPDATE products SET title=?, description=?, price=?, location=?, category=?, image=?, userId=? WHERE id=?;";

    private static final String select_sql = "SELECT * FROM  products WHERE id=?;";


    private static final String select_img_sql = "SELECT image FROM  products WHERE id=?;";

    private static final String insert_sql = "INSERT INTO products (id,title,description,price,location,category,image,userId) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

    private static final String delete_sql = "DELETE FROM products WHERE id=?;";

    private static final String delete_all_sql_by_id = "DELETE FROM products WHERE userId=?;"; // delete Products by User

    private static final String select_all_sql_by_id = "SELECT * FROM products WHERE userId=?;"; // get Products of User

    private static final String select_all_sql = "SELECT * FROM products;"; // get all Products


    public List<Product> getAllProducts(Connection conn)
            throws SQLException {

        List<Product> results = new ArrayList<Product>();

        ResultSet rs = null;
        PreparedStatement ps = null;
        try {

            ps = conn.prepareStatement(select_all_sql);

            rs = ps.executeQuery();

            while (rs.next()) {

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
                results.add(product);

            }

        } catch (SQLException e) {
            throw e;

        } catch (Throwable e) {
            e.printStackTrace();

        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return results;
    }

    public List<Product> getAllProductsByUser(String userId, Connection conn)
            throws SQLException {

        List<Product> results = new ArrayList<Product>();

        if (userId == null) {
            return results;
        }

        ResultSet rs = null;
        PreparedStatement ps = null;
        try {

            ps = conn.prepareStatement(select_all_sql_by_id);

            ps.setString(1, userId);

            rs = ps.executeQuery();

            while (rs.next()) {

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
                results.add(product);

            }

        } catch (SQLException e) {
            throw e;

        } catch (Throwable e) {
            e.printStackTrace();

        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return results;
    }

    public byte[] getImage(String productId, Connection conn)
            throws SQLException {

        byte[] result = null;

        ResultSet rs = null;
        PreparedStatement ps = null;
        try {

            ps = conn.prepareStatement(select_img_sql);

            ps.setString(1, productId);

            rs = ps.executeQuery();

            while (rs.next()) {

                java.sql.Blob imageBlob = rs.getBlob(1);
                if (imageBlob != null) {
                    result = imageBlob.getBytes(1, (int) imageBlob.length());
                }
            }

        } catch (SQLException e) {
            throw e;

        } catch (Throwable e) {
            e.printStackTrace();

        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    public List<Product> getProduct(String id, Connection conn)
            throws SQLException {

        List<Product> results = new ArrayList<Product>();

        if (id == null) {
            return results;
        }

        ResultSet rs = null;
        PreparedStatement ps = null;
        try {

            ps = conn.prepareStatement(select_sql);

            ps.setString(1, id);

            rs = ps.executeQuery();

            while (rs.next()) {

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
                results.add(product);

            }

        } catch (SQLException e) {
            throw e;

        } catch (Throwable e) {
            e.printStackTrace();

        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return results;
    }

    public boolean insertProduct(Product obj, Connection conn) throws SQLException{

        boolean result = false;
        ResultSet rs = null;
        ResultSet rs1 = null;
        PreparedStatement ps = null;
        PreparedStatement stt = null;

        try {

            String id = obj.getId();
            String title = obj.getTitle();
            String description = obj.getDescription();
            String location = obj.getLocation();
            String price = obj.getPrice();
            String category = obj.getCategory();
            byte[] imageBytes = obj.getImage();

            String userId = obj.getUserId();


            if (imageBytes == null) {
                try {
                    imageBytes = getImage(id, conn);
                }catch (Throwable e){
                    System.out.println("no image for product "+ id);
                }
            }


            result=true;
            stt = (PreparedStatement) conn.prepareStatement(insert_sql);
            stt.setString(1, id);
            stt.setString(2, title);
            stt.setString(3, description);
            stt.setString(4, price);
            stt.setString(5, location);
            stt.setString(6, category);
            if (imageBytes != null) {
                InputStream is = new ByteArrayInputStream(imageBytes);
                stt.setBlob(7, is);

            } else {

                stt.setNull(7, Types.BLOB);
            }
            stt.setString(8, userId);


            if (stt.execute()) {
                rs1 = stt.getResultSet();
                if (rs1.next()) {
                    // its execute update
                    ps = (PreparedStatement) conn.prepareStatement(update_sql);
                    ps.setString(1, id);
                    ps.setString(2, title);
                    ps.setString(3, description);
                    if (imageBytes != null) {
                        InputStream is = new ByteArrayInputStream(imageBytes);
                        ps.setBlob(7, is);

                    } else {

                        ps.setNull(7, Types.BLOB);
                    }
                    ps.setString(5, location);
                    ps.setString(6, category);
                    ps.setString(4, price);
                    ps.setString(8, userId);

                    // where
                    ps.execute();

                    result = true;

                } else {

                    // its execute insert
                    ps = (PreparedStatement) conn.prepareStatement(insert_sql);
                    ps.setString(1, id);
                    ps.setString(2, title);
                    ps.setString(3, description);


                    if (imageBytes != null) {
                        InputStream is = new ByteArrayInputStream(imageBytes);
                        ps.setBlob(7, is);

                    } else {

                        ps.setNull(7, Types.BLOB);
                    }


                    ps.setString(5, location);
                    ps.setString(6, category);
                    ps.setString(4, price);
                    ps.setString(8, userId);

                    ps.execute();

                    result = true;

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
            if (rs1 != null) {
                try {
                    rs1.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

            if (stt != null) {
                try {
                    stt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;

    }

    public boolean deleteAllProductsByUser(User user, Connection conn)
            throws SQLException {

        boolean result = false;

        PreparedStatement ps = null;

        try {

            ps = (PreparedStatement) conn.prepareStatement(delete_all_sql_by_id);

            ps.setString(1, user.getName());

            ps.execute();

            result = true;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {

                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    public boolean deleteProduct(Product obj, Connection conn) throws SQLException {

        boolean result = false;
        PreparedStatement ps = null;

        try {

            if (obj != null) {

                ps = (PreparedStatement) conn.prepareStatement(delete_sql);

                String id = obj.getId();

                ps.setString(1, id);

                ps.execute();

                result = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {

                    e.printStackTrace();
                }
            }
        }

        return result;
    }

}