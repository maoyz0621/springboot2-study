/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.springboot2servlet.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义Servlet
 * @author maoyz0621 on 19-1-20
 * @version: v1.0
 */
@WebServlet(urlPatterns = {"/myServlet"})
public class MyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getOutputStream().print("hello Servlet");
    }
}
