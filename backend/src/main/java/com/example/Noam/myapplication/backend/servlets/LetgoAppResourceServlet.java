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
    private static final int GET_ALL_PRODUCTS_JSON_REQ = 0;
    //private static final int INSERT_TUMBLER_REQ = 1;
    //private static final int DELETE_TUMBLER_REQ = 2;
    //private static final int INSERT_POST_REQ = 3;

    private static final int DELETE_PRODUCT_REQ = 3;
    private static final int GET_PRODUCT_IMAGE_REQ = 4;

    private static final int GET_PRODUCTS_OF_USER_JSON_REQ = 5;

    private static final String USER_ID = "userId";
    //private static final String USER_PASS = "password";

    private static final String RESOURCE_FAIL_TAG = "{\"result_code\":0}";
    private static final String RESOURCE_SUCCESS_TAG = "{\"result_code\":1}";

    private static final String PRODUCT_ID = "id";
    private static final String PRODUCT_TITLE = "title";
    private static final String PRODUCT_DESCRIPTION = "description";
    private static final String PRODUCT_LOCATION = "location";
    private static final String PRODUCT_CATEGORY = "category";
    private static final String PRODUCT_PRICE = "price";



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
            System.out.println("TumblerAppResourceServlet:: req code ==>" + reqNo);
            //while (retry > 0) {

            try {

                switch (reqNo) {

                    // == folder apis
                    case GET_ALL_PRODUCTS_JSON_REQ: {

                        conn = ConnPool.getInstance().getConnection();
                        ProductsResProvider postsResProvider = new ProductsResProvider();
                        List<Product> postsList = postsResProvider
                                .getAllPosts(conn);
                        String resultJson = Product.toJson(postsList);

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

//					case INSERT_TUMBLER_REQ: {
//						String id = req.getParameter(TUMBLER_ID);
//						String title = req.getParameter(TUMBLER_PASS);
//						respPage = RESOURCE_FAIL_TAG;
//						resp.addHeader("Content-Type",
//								"application/json; charset=UTF-8");
//						conn = ConnPool.getInstance().getConnection();
//						TumblerResProvider tumblerResProvider = new TumblerResProvider();
//
//						TumblerInfo tumblerInfo = new TumblerInfo(id, title);
//						if (tumblerResProvider.insertTumbler(tumblerInfo, conn)) {
//							respPage = RESOURCE_SUCCESS_TAG;
//						}
//						PrintWriter pw = resp.getWriter();
//						pw.write(respPage);
//
//						//retry = 0;
//						break;
//					}
//
//					case DELETE_TUMBLER_REQ: {
//						String id = req.getParameter(TUMBLER_ID);
//						respPage = RESOURCE_FAIL_TAG;
//						resp.addHeader("Content-Type",
//								"application/json; charset=UTF-8");
//						conn = ConnPool.getInstance().getConnection();
//						TumblerResProvider tumblerResProvider = new TumblerResProvider();
//
//						TumblerInfo tumblerInfo = new TumblerInfo(id);
//						if (tumblerResProvider.deleteTumbler(tumblerInfo, conn)) {
//							respPage = RESOURCE_SUCCESS_TAG;
//						}
//						PrintWriter pw = resp.getWriter();
//						pw.write(respPage);
//
//						//retry = 0;
//						break;
//					}
//
//						case INSERT_POST_REQ: {
//							String id = req.getParameter(POST_ID);
//
//							String title = req.getParameter(POST_TITLE);
//
//							String content = req.getParameter(POST_CONTENT);
//
//							String tumblerId = req.getParameter(TUMBLER_ID);
//
//							String tag = req.getParameter(POST_TAG);
//
//							respPage = RESOURCE_FAIL_TAG;
//							resp.addHeader("Content-Type",
//									"application/json; charset=UTF-8");
//							conn = ConnPool.getInstance().getConnection();
//							PostsResProvider postsResProvider = new PostsResProvider();
//
//							PostInfo post = new PostInfo(id);
//							post.setTitle(title);
//							post.setContent(content);
//							post.setTumblerId(tumblerId);
//							post.setTag(tag);
//
//							if (postsResProvider.insertPostInfo(post, conn)) {
//								respPage = RESOURCE_SUCCESS_TAG;
//							}
//							PrintWriter pw = resp.getWriter();
//							pw.write(respPage);
//
//							//retry = 0;
//							break;
//						}

                    case DELETE_PRODUCT_REQ: {
                        String id = req.getParameter(PRODUCT_ID);
                        respPage = RESOURCE_FAIL_TAG;
                        resp.addHeader("Content-Type",
                                "application/json; charset=UTF-8");
                        conn = ConnPool.getInstance().getConnection();
                        ProductsResProvider postsResProvider = new ProductsResProvider();
                        Product postInfo = new Product(id);
                        if (postsResProvider.deleteProduct(postInfo, conn)) {
                            respPage = RESOURCE_SUCCESS_TAG;
                        }
                        PrintWriter pw = resp.getWriter();
                        pw.write(respPage);

                        //retry = 0;
                        break;
                    }

                    case GET_PRODUCTS_OF_USER_JSON_REQ: {

                        String id = req.getParameter(USER_ID);
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

