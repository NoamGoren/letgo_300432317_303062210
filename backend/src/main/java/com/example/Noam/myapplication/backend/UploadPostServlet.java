package com.example.Noam.myapplication.backend;

import com.example.Noam.myapplication.backend.core.PostInfo;
import com.example.Noam.myapplication.backend.operations.ConnPool;
import com.example.Noam.myapplication.backend.operations.PostsResProvider;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.IOException;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Noam on 12/27/2017.
 */

public class UploadPostServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FileItemFactory factory= new DiskFileItemFactory();
        ServletFileUpload upload =new ServletFileUpload(factory);

        String id =null;
        String title=null;
        String content= null;
        String tumblerId=null;
        String tag=null;
        byte [] img =null;
        try {



            List<FileItem> items =upload.parseRequest(req);
            Iterator<FileItem> itr = items.iterator();

            ServletOutputStream so =resp.getOutputStream();
            while (itr.hasNext()){
               FileItem item= itr.next();
                if (item.isFormField()){
                    String parameterId=item.getFieldName();
                    String parameterValue= item.getString();

                    if(parameterId.equals("id")){
                        id=parameterValue;

                    }else if(parameterId.equals("title")){
                        title=parameterValue;

                    }else if(parameterId.equals("content")){
                        content=parameterValue;

                    }else if(parameterId.equals("tag")){
                        tag=parameterValue;

                    }else if(parameterId.equals("tumbler_id")){

                    }

                }else{
                    String imageName = item.getName();
                    img=item.get();
                }
            }

            //TODO check null
            Connection conn = ConnPool.getInstance().getConnection();
            PostsResProvider postsProvider= new PostsResProvider();
            PostInfo post = new PostInfo( id,  tumblerId,  title,
                     content, tag, img);
            boolean result =postsProvider.insertPost(post,conn);

            if (result=true){
                so.println("pass");
            }else{
                so.println("fail");
            }
            so.close();
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
    }
}
