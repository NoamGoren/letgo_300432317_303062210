package com.example.Noam.myapplication.backend;

import com.example.Noam.myapplication.backend.core.PostInfo;
import com.example.Noam.myapplication.backend.core.Product;
import com.example.Noam.myapplication.backend.operations.ConnPool;
import com.example.Noam.myapplication.backend.operations.ProductsResProvider;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by u31_7101 on 25/12/2017.
 */

public class LetgoAppResourceServlet extends HttpServlet {


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

         String reqCode = req.getParameter("req");
        Connection conn = null;
         int reqClient = -1;
         if(reqCode!=null){
             reqClient = Integer.parseInt(reqCode);
             switch (reqClient){
                 case 0:{
                     String userId = req.getParameter("userId");
                     conn = ConnPool.getInstance().getConnection();
                     ProductsResProvider productsProvider = new ProductsResProvider();
                     try {
                         List<Product> products= productsProvider.getAllProductsByUser(userId, conn);

                         if (products!=null &&products.size()>0){
                             String responseData= Product.toJson(products);
                             resp.addHeader("Content-Type","application/json; charset=UTF-8");
                             resp.getWriter().println(responseData);
                         }
                     } catch (SQLException e) {
                         e.printStackTrace();
                     }
                     ConnPool.getInstance().returnConnection(conn);
                 }
                 case 1:{
                     String productId= req.getParameter("id");
                     conn= ConnPool.getInstance().getConnection();
                     ProductsResProvider productsProvider= new ProductsResProvider();
                     try {
                         byte[] imgBlob= productsProvider.getImg(productId,conn);
                         if (imgBlob!=null&&imgBlob.length>0){
                             ServletOutputStream so= resp.getOutputStream();
                             so.write(imgBlob);
                         }
                     }catch (SQLException e){
                         e.printStackTrace();
                     }
                     ConnPool.getInstance().returnConnection(conn);

                 }


             }
         }




    }
}
