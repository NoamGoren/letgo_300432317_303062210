package com.example.Noam.myapplication.backend.operations;

import com.example.Noam.myapplication.backend.core.PostInfo;

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

public class PostsResProvider {

    public List<PostInfo> getAllPostsByTumbler(String tumblerId, Connection conn) throws SQLException{
        List<PostInfo> result = new ArrayList<PostInfo>();
        if(tumblerId==null){
            return result;
        }
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("select * from posts where tumbler_id=?");
            ps.setString(1,tumblerId);
            rs= ps.executeQuery();
            while(rs.next()){
                String postId =rs.getString(1);
                String postTitle = rs.getString(2);
                String content =rs.getString(3);
                Blob imgblob = rs.getBlob(4);
                byte[] img= null;
                if(imgblob!=null){
                    img = imgblob.getBytes(1, (int)imgblob.length());
                }
                String tag = rs.getString(5);
                String tumbler = rs.getString(6);

                PostInfo post = new PostInfo( postId,  tumbler,  postTitle,
                         content,  tag,  img);
                result.add(post);
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


    public byte[] getImg(String postId, Connection conn) throws SQLException {
        byte[] result= null;
        ResultSet rs= null;
        PreparedStatement ps= null;
        try{
            ps= conn.prepareStatement("select img from posts where id=?");
            ps.setString(1,postId);
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

    public boolean insertPost(PostInfo post, Connection conn) {
        //TODO add code here
        return false;
    }
}
