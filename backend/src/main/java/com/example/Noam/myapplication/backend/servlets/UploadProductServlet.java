package com.example.Noam.myapplication.backend.servlets;


import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.example.Noam.myapplication.backend.operations.ConnPool;
import com.example.Noam.myapplication.backend.operations.ProductsResProvider;
import com.example.Noam.myapplication.backend.objects.Product;

/**
 * Servlet implementation class UploadPostServlet
 */

public class UploadProductServlet extends HttpServlet {


    private static final long serialVersionUID = 1L;


    private static final String RESOURCE_FAIL_TAG = "{\"result_code\":0}";
    private static final String RESOURCE_SUCCESS_TAG = "{\"result_code\":1}";

    //TODO why  the names are "post_id" and not "id"
    private static final String PRODUCT_ID = "id";
    private static final String PRODUCT_TITLE = "title";
    private static final String PRODUCT_DESCRIPTION = "description";
    private static final String PRODUCT_LOCATION = "location";
    private static final String PRODUCT_CATEGORY = "category";
    private static final String PRODUCT_PRICE = "price";
    private static final String USER_ID = "userId";


    //public static final int DB_RETRY_TIMES=5;


    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Commons file upload classes are specifically instantiated
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(500000);

        ServletFileUpload upload = new ServletFileUpload(factory);
        ServletOutputStream out = null;

        //int retry = DB_RETRY_TIMES;
        Connection conn = null;



        String id = null;
        String title = null;
        String description = null;
        String location=null;
        String category=null;
        String price=null;
        String userId=null;


        String fileName = null;

        byte [] image= null;
        String respPage = RESOURCE_FAIL_TAG;
        try {

            System.out.println("======= upload PostInfo with image Servlet =======");
            // Parse the incoming HTTP request
            // Commons takes over incoming request at this point
            // Get an iterator for all the data that was sent
            List<FileItem> items = upload.parseRequest(req);
            Iterator<FileItem> iter = items.iterator();

            // Set a response content type
            resp.setContentType("text/html");

            // Setup the output stream for the return XML data
            out = resp.getOutputStream();

            // Iterate through the incoming request data
            while (iter.hasNext()) {
                // Get the current item in the iteration
                FileItem item = (FileItem) iter.next();

                // If the current item is an HTML form field
                if (item.isFormField()) {
                    // If the current item is file data

                    // If the current item is file data
                    String fieldname = item.getFieldName();
                    String fieldvalue = item.getString();

                    System.out.println(fieldname + "=" + fieldvalue);


                    if (fieldname.equals(PRODUCT_ID)) {
                        id =fieldvalue;
                    } else if (fieldname.equals(PRODUCT_TITLE)) {
                        title = fieldvalue;
                    } else if (fieldname.equals(PRODUCT_DESCRIPTION)) {
                        description = fieldvalue;
                    } else if (fieldname.equals(PRODUCT_LOCATION)) {
                        location = fieldvalue;
                    } else if (fieldname.equals(PRODUCT_CATEGORY)){
                        category = fieldvalue;
                    } else if(fieldname.equals(PRODUCT_PRICE)) {
                        price=fieldvalue;
                    }else if(fieldname.equals(USER_ID)) {
                        userId=fieldvalue;
                    }


                } else {

                    //fileName = item.getName();
                    image=item.get();

                }
            }

            //while (retry > 0) {

            try {

                conn = ConnPool.getInstance().getConnection();
                ProductsResProvider itemResProvider = new ProductsResProvider();
                Product postInfo = new Product(id, title, description, price, location,category,image,userId);
                if(itemResProvider.insertProduct(postInfo, conn)) {
                    respPage = RESOURCE_SUCCESS_TAG;
                }

                //retry = 0;

            } catch (SQLException e) {
                e.printStackTrace();
                //retry--;
            } catch (Throwable t) {
                t.printStackTrace();
                //retry = 0;
            } finally {
                if (conn != null) {
                    ConnPool.getInstance().returnConnection(conn);
                }
            }

            //	}

            out.println(respPage);
            out.close();


        } catch (FileUploadException fue) {
            fue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

