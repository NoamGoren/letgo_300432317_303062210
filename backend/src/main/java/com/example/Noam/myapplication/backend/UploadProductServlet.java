package com.example.Noam.myapplication.backend;

import com.example.Noam.myapplication.backend.core.PostInfo;
import com.example.Noam.myapplication.backend.core.Product;
import com.example.Noam.myapplication.backend.operations.ConnPool;
import com.example.Noam.myapplication.backend.operations.ProductsResProvider;

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

public class UploadProductServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FileItemFactory factory= new DiskFileItemFactory();
        ServletFileUpload upload =new ServletFileUpload(factory);

        String id=null;
        String title=null;
        String description=null;
        byte[] image1 = null;
        String location=null;
        String category=null;
        String price=null;
        String userId=null;

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

                    }else if(parameterId.equals("description")){
                        description=parameterValue;

                    }else if(parameterId.equals("location")){
                        location=parameterValue;

                    }else if(parameterId.equals("category")){
                        category=parameterValue;

                    }else if(parameterId.equals("price")){
                        price= parameterValue;

                    }else if(parameterId.equals("user_id")){

                    }

                }else{
                    String imageName = item.getName();
                    image1=item.get();
                }
            }

            //TODO check null
            Connection conn = ConnPool.getInstance().getConnection();
            ProductsResProvider productsProvider= new ProductsResProvider();
            Product product = new Product( id,  title,  description, price, location, category,image1,userId);
            boolean result =productsProvider.insertProduct(product,conn);

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
