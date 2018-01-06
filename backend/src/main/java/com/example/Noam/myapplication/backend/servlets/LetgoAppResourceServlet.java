package com.example.Noam.myapplication.backend.servlets;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.Noam.myapplication.backend.operations.ConnPool;
import com.example.Noam.myapplication.backend.operations.UsersResProvider;
import com.example.Noam.myapplication.backend.operations.ProductsResProvider;
import com.example.Noam.myapplication.backend.objects.User;
import com.example.Noam.myapplication.backend.objects.Product;
import com.example.Noam.myapplication.backend.utils.FilesUtils;

/**
 * Servlet implementation class TumblerAppResourceServlet
 */
public class LetgoAppResourceServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // ========
   //users
    private static final int INSERT_USER_REQ = 1;//working
    private static final int DELETE_USER_REQ = 2;//working
    private static final int GET_ALL_USERS_JSON_REQ = 3;//working
    private static final int GET_USER_JSON_REQ = 4; //working

    //products
    private static final int GET_ALL_PRODUCTS_JSON_REQ = 5; //working
    private static final int INSERT_PRODUCT_REQ = 6;//working
    private static final int DELETE_PRODUCT_REQ = 7;//working
    private static final int GET_PRODUCT_IMAGE_REQ = 8;//working
    private static final int GET_PRODUCTS_OF_USER_JSON_REQ = 9; //working
    private static final int GET_PRODUCT_JSON_REQ = 10; //working

    private static final String USER_NAME = "name";
    //private static final String USER_PASS = "password";

    private static final String RESOURCE_FAIL_TAG = "{\"result_code\":0}";
    private static final String RESOURCE_SUCCESS_TAG = "{\"result_code\":1}";

    private static final String PRODUCT_ID = "id";
    private static final String PRODUCT_TITLE = "title";
    private static final String PRODUCT_DESCRIPTION = "description";
    private static final String PRODUCT_LOCATION = "location";
    private static final String PRODUCT_CATEGORY = "category";
    private static final String PRODUCT_PRICE = "price";
    private static final String PRODUCT_USERID = "userId";



    private static final String REQ = "req";

    //public static final int DB_RETRY_TIMES = 5;


    public void init(ServletConfig config) throws ServletException {

        super.init();

        String tmp = config.getServletContext().getInitParameter("localAppDir");
        if (tmp != null) {
            FilesUtils.appDirName = config.getServletContext().getRealPath(tmp);
            System.out.println(FilesUtils.appDirName);

        }

    }

    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String respPage = null;
        String userReq = req.getParameter(REQ);
        Connection conn = null;
        //int retry = DB_RETRY_TIMES;

        if (userReq != null) {

            int reqNo = Integer.valueOf(userReq);
            System.out.println("LetgoAppResourceServlet:: req code ==>" + reqNo);
            //while (retry > 0) {

            try {

                switch (reqNo) {

                    // == folder apis
                    case GET_ALL_PRODUCTS_JSON_REQ: {

                        conn = ConnPool.getInstance().getConnection();
                        ProductsResProvider productsResProvider = new ProductsResProvider();
                        List<Product> productsList = productsResProvider
                                .getAllProducts(conn);
                        String resultJson = Product.toJson(productsList);

                        if (resultJson != null && !resultJson.isEmpty()) {
                            respPage = resultJson;
                            resp.addHeader("Content-Type",
                                    "application/json; charset=UTF-8");
                            PrintWriter pw = resp.getWriter();
                            pw.write(respPage);
                        } else {
                            resp.sendError(404);
                        }

                        //retry = 0;
                        break;
                    }
                    case GET_ALL_USERS_JSON_REQ: {

                        conn = ConnPool.getInstance().getConnection();
                        UsersResProvider usersResProvider = new UsersResProvider();
                        List<User> userList = usersResProvider
                                .getAllUsers(conn);
                        String resultJson = User.toJson(userList);

                        if (resultJson != null && !resultJson.isEmpty()) {
                            respPage = resultJson;
                            resp.addHeader("Content-Type",
                                    "application/json; charset=UTF-8");
                            PrintWriter pw = resp.getWriter();
                            pw.write(respPage);
                        } else {
                            resp.sendError(404);
                        }

                        //retry = 0;
                        break;
                    }

					case INSERT_USER_REQ: {
                        String name = req.getParameter("name");
                        respPage = RESOURCE_FAIL_TAG;
					resp.addHeader("Content-Type",
								"application/json; charset=UTF-8");
						conn = ConnPool.getInstance().getConnection();
						UsersResProvider usersResProvider = new UsersResProvider();

						User user = new User(name);
						if (usersResProvider.insertUser(user, conn)) {
							respPage = RESOURCE_SUCCESS_TAG;
						}
						PrintWriter pw = resp.getWriter();
						pw.write(respPage);

						//retry = 0;
						break;

				}

						case INSERT_PRODUCT_REQ: {
							String id = req.getParameter(PRODUCT_ID);

							String title = req.getParameter(PRODUCT_TITLE);

							String description = req.getParameter(PRODUCT_DESCRIPTION);

							String price = req.getParameter(PRODUCT_PRICE);

							String location = req.getParameter(PRODUCT_LOCATION);
                            String category = req.getParameter(PRODUCT_CATEGORY);

                            String userId = req.getParameter(PRODUCT_USERID);


							respPage = RESOURCE_FAIL_TAG;
							resp.addHeader("Content-Type",
									"application/json; charset=UTF-8");
							conn = ConnPool.getInstance().getConnection();
							ProductsResProvider productsResProvider = new ProductsResProvider();

							Product product = new Product(id,title,description,price,location,category,userId);
//                            product.setId(id);
//                            product.setTitle(title);
//                            product.setDescription(description);
//                            product.setPrice(price);
//                            product.setLocation(location);
//                            product.setCategory(category);
//                            product.setUserId(userId);

							if (productsResProvider.insertProduct(product, conn)) {
								respPage = RESOURCE_SUCCESS_TAG;
							}
							PrintWriter pw = resp.getWriter();
							pw.write(respPage);

							//retry = 0;
							break;
						}

                    case DELETE_PRODUCT_REQ: {
                        String id = req.getParameter(PRODUCT_ID);
                        respPage = RESOURCE_FAIL_TAG;
                        resp.addHeader("Content-Type",
                                "application/json; charset=UTF-8");
                        conn = ConnPool.getInstance().getConnection();
                        ProductsResProvider productsResProvider = new ProductsResProvider();
                        Product product = new Product(id);
                        if (productsResProvider.deleteProduct(product, conn)) {
                            respPage = RESOURCE_SUCCESS_TAG;
                        }
                        PrintWriter pw = resp.getWriter();
                        pw.write(respPage);

                        //retry = 0;
                        break;
                    }

                    case DELETE_USER_REQ: {
                        String id = req.getParameter("name");
                        respPage = RESOURCE_FAIL_TAG;
                        resp.addHeader("Content-Type",
                                "application/json; charset=UTF-8");
                        conn = ConnPool.getInstance().getConnection();
                        UsersResProvider usersResProvider = new UsersResProvider();
                        User user = new User(id);
                        if (usersResProvider.deleteUser(user, conn)) {
                            respPage = RESOURCE_SUCCESS_TAG;
                        }
                        PrintWriter pw = resp.getWriter();
                        pw.write(respPage);

                        //retry = 0;
                        break;
                    }

                    case GET_PRODUCTS_OF_USER_JSON_REQ: {

                        String id = req.getParameter("userId");
                        conn = ConnPool.getInstance().getConnection();
                        ProductsResProvider productsResProvider = new ProductsResProvider();

                        List<Product> itemsList = productsResProvider.getAllProductsByUser(
                                id, conn);
                        String resultJson = Product.toJson(itemsList);

                        if (resultJson != null && !resultJson.isEmpty()) {
                            respPage = resultJson;
                            resp.addHeader("Content-Type",
                                    "application/json; charset=UTF-8");
                            PrintWriter pw = resp.getWriter();
                            pw.write(respPage);
                        } else {
                            resp.sendError(404);
                        }

                        //retry = 0;
                        break;
                    }

                    case GET_PRODUCT_JSON_REQ: {

                        String id = req.getParameter("id");
                        conn = ConnPool.getInstance().getConnection();
                        ProductsResProvider productsResProvider = new ProductsResProvider();

                        List<Product> itemsList = productsResProvider.getProduct(
                                id, conn);
                        String resultJson = Product.toJson(itemsList);

                        if (resultJson != null && !resultJson.isEmpty()) {
                            respPage = resultJson;
                            resp.addHeader("Content-Type",
                                    "application/json; charset=UTF-8");
                            PrintWriter pw = resp.getWriter();
                            pw.write(respPage);
                        } else {
                            resp.sendError(404);
                        }

                        //retry = 0;
                        break;
                    }

                    case GET_USER_JSON_REQ: {

                        String id = req.getParameter("name");
                        conn = ConnPool.getInstance().getConnection();
                        UsersResProvider usersResProvider = new UsersResProvider();

                        List<User> itemsList = usersResProvider.getUser(
                                id, conn);
                        String resultJson = User.toJson(itemsList);

                        if (resultJson != null && !resultJson.isEmpty()) {
                            respPage = resultJson;
                            resp.addHeader("Content-Type",
                                    "application/json; charset=UTF-8");
                            PrintWriter pw = resp.getWriter();
                            pw.write(respPage);
                        } else {
                            resp.sendError(404);
                        }

                        //retry = 0;
                        break;
                    }

                    case GET_PRODUCT_IMAGE_REQ: {
                        String id = req.getParameter(PRODUCT_ID);
                        respPage = RESOURCE_FAIL_TAG;

                        conn = ConnPool.getInstance().getConnection();
                        ProductsResProvider productsResProvider = new ProductsResProvider();

                        byte[] imgBlob = productsResProvider.getImage(id, conn);

                        if (imgBlob != null && imgBlob.length > 0) {
//                            resp.setContentType("application/x-download");
//                            resp.setHeader("Content-disposition", "attachment; filename=" + "post.png");
                            ServletOutputStream os = resp.getOutputStream();
                            os.write(imgBlob);
                        } else {
                            resp.sendError(404);
                        }

                        //retry = 0;
                        break;
                    }



                    default:
                        //retry = 0;
                }

            } catch (SQLException e) {
                e.printStackTrace();
                //	retry--;
            } catch (Throwable t) {
                t.printStackTrace();
                //	retry = 0;
            } finally {
                if (conn != null) {
                    ConnPool.getInstance().returnConnection(conn);
                }
            }
            //}
        }

    }

}

