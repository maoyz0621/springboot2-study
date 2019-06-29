/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.springboot2jetty.servlet;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author maoyz0621 on 19-1-20
 * @version: v1.0
 */
@WebServlet(urlPatterns = {"/myAsyncServlet"}, asyncSupported = true)
public class MyAsyncServlet extends HttpServlet {

    /**
     * 开启异步非阻塞
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 开启异步
        AsyncContext context = req.startAsync();

        context.start(() -> {
            try {
                resp.getOutputStream().print("hello MyAsyncServlet");
                // 触发完成
                context.complete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
