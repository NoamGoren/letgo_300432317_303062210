package com.example.Noam.myapplication.backend;

import com.example.Noam.myapplication.backend.core.PostInfo;
import com.example.Noam.myapplication.backend.operations.ConnPool;
import com.example.Noam.myapplication.backend.operations.PostsResProvider;

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

public class TumblerAppResourceServlet extends HttpServlet {


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

         String reqCode = req.getParameter("req");
        Connection conn = null;
         int reqClient = -1;
         if(reqCode!=null){
             reqClient = Integer.parseInt(reqCode);
             switch (reqClient){
                 case 0:{
                     String tumblerId = req.getParameter("tumbler_id");
                     conn = ConnPool.getInstance().getConnection();
                     PostsResProvider postsProvider = new PostsResProvider();
                     try {
                         List<PostInfo> posts= postsProvider.getAllPostsByTumbler(tumblerId, conn);

                         if (posts!=null &&posts.size()>0){
                             String responseData= PostInfo.toJson(posts);
                             resp.addHeader("Content-Type","application/json; charset=UTF-8");
                             resp.getWriter().println(responseData);
                         }
                     } catch (SQLException e) {
                         e.printStackTrace();
                     }
                     ConnPool.getInstance().returnConnection(conn);
                 }
                 case 1:{
                     String postId= req.getParameter("post_id");
                     conn= ConnPool.getInstance().getConnection();
                     PostsResProvider postsProvider= new PostsResProvider();
                     try {
                         byte[] imgBlob= postsProvider.getImg(postId,conn);
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
