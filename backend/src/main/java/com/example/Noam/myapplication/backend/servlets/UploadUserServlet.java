package com.example.Noam.myapplication.backend.servlets;


import java.io.IOException;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.example.Noam.myapplication.backend.objects.User;
import com.example.Noam.myapplication.backend.operations.ConnPool;
import com.example.Noam.myapplication.backend.operations.UsersResProvider;

/**
 * Servlet implementation class UploadPostServlet
 */

public class UploadUserServlet extends HttpServlet {


    private static final long serialVersionUID = 1L;


    private static final String RESOURCE_FAIL_TAG = "{\"result_code\":0}";
    private static final String RESOURCE_SUCCESS_TAG = "{\"result_code\":1}";



    private static final String USER_NAME = "name";


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



        String name = null;


        String fileName = null;


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


                    if (fieldname.equals(USER_NAME)) {
                        name =fieldvalue;
                    }

                } else {

                }
            }

            //while (retry > 0) {

            try {

                conn = ConnPool.getInstance().getConnection();
                UsersResProvider userResProvider = new UsersResProvider();
                User user = new User(name);
                if(userResProvider.insertUser(user,conn)) {
                    respPage = RESOURCE_SUCCESS_TAG;
                }

                //retry = 0;

            }  catch (Throwable t) {
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

